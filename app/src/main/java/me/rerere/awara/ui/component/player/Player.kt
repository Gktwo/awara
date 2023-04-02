package me.rerere.awara.ui.component.player

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Pause
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

private const val TAG = "Player"

@Composable
fun Player(
    modifier: Modifier,
    state: PlayerState,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    Box(
        modifier = modifier
    ) {
        PlayerBase(
            state = state,
            modifier = Modifier.matchParentSize()
        )
        CompositionLocalProvider(LocalContentColor provides Color.White) {
            PlayerController(
                state = state,
                modifier = Modifier.matchParentSize(),
                navigationIcon = navigationIcon,
                actions = actions
            )
        }
    }
}

@Composable
private fun PlayerController(
    modifier: Modifier,
    state: PlayerState,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    // 自动隐藏Controller
    var visible by remember { mutableStateOf(true) }
    var lastInteract by remember { mutableStateOf(System.currentTimeMillis()) }
    LaunchedEffect(Unit) {
        visible = true
        lastInteract = System.currentTimeMillis()
        while (true) {
            delay(1000)
            if (System.currentTimeMillis() - lastInteract > 3000 && state.playing) {
                visible = false
            }
        }
    }

    // 拖动进度手势
    val density = LocalDensity.current
    var dragTracker by remember {
        mutableStateOf(0L)
    }
    var currentDragTime by remember {
        mutableStateOf("00:00")
    }
    var dragging by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        if (state.playing) state.pause() else state.play()
                    },
                    onTap = {
                        lastInteract = System.currentTimeMillis()
                        visible = !visible
                    }
                )
            }
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragStart = {
                        dragTracker = state.player.currentPosition
                        dragging = true
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        val dp = with(density) { dragAmount.toDp() }
                        dragTracker += dp.value.toLong() * 300
                        currentDragTime = dragTracker.toTime()
                    },
                    onDragEnd = {
                        dragging = false
                        state.seekTo(dragTracker)
                    },
                    onDragCancel = {
                        dragging = false
                    },
                )
            }
            .fillMaxSize()
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(
                modifier = modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxSize()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    navigationIcon?.invoke()

                    Text(
                        text = state.currentMediaItem?.mediaMetadata?.title?.toString()
                            ?: "Player",
                        maxLines = 1,
                        style = MaterialTheme.typography.titleLarge,
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    actions()
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ProgressBar(state) {
                        lastInteract = System.currentTimeMillis()
                    }
                }
            }
        }

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if(visible && state.loading) {
                CircularProgressIndicator(
                    color = Color.White
                )
            }

            if(dragging) {
                Text(currentDragTime)
            }
        }
    }
}

@Composable
private fun RowScope.ProgressBar(state: PlayerState, onTouch: () -> Unit) {
    val currentProgress by produceState(
        initialValue = 0L,
        producer = {
            value = state.player.currentPosition
            while (true) {
                delay(500)
                value = state.player.currentPosition
            }
        }
    )
    val percent by remember {
        derivedStateOf {
            if (state.duration == 0L) {
                0f
            } else {
                currentProgress.toFloat() / state.duration.toFloat()
            }
        }
    }

    // Play/Pause button
    if (state.playing) {
        IconButton(
            onClick = {
                state.player.pause()
            }
        ) {
            Icon(Icons.Outlined.Pause, "Pause")
        }
    } else {
        IconButton(
            onClick = {
                state.player.play()
            }
        ) {
            Icon(Icons.Outlined.PlayArrow, "Play")
        }
    }

    // Progress bar
    if (state.state == PlayerState.State.BUFFERING) {
        LinearProgressIndicator(
            modifier = Modifier.weight(1f),
        )
    } else {
        var sliding by remember { mutableStateOf(false) }
        var sliderValue by remember { mutableStateOf(percent) }
        Slider(
            value = if (sliding) sliderValue else percent,
            onValueChange = {
                sliding = true
                sliderValue = it
                onTouch()
            },
            onValueChangeFinished = {
                val newPosition = (sliderValue * state.duration).toLong()
                state.seekTo(newPosition)
                sliding = false
            },
            modifier = Modifier.weight(1f),
        )
    }

    // Time
    Text(
        text = "${currentProgress.toTime()} / ${state.duration.toTime()}",
        maxLines = 1,
        style = MaterialTheme.typography.titleSmall,
    )
}

private fun Long.toTime(): String {
    if (this <= 0) return "00:00"
    val second = this / 1000
    val minute = second / 60
    val hour = minute / 60
    return if (hour > 0) {
        "%02d:%02d:%02d".format(hour, minute % 60, second % 60)
    } else {
        "%02d:%02d".format(minute % 60, second % 60)
    }
}