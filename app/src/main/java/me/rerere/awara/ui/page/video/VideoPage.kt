package me.rerere.awara.ui.page.video

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Fullscreen
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.media3.common.MediaItem
import kotlinx.coroutines.flow.collectLatest
import me.rerere.awara.data.entity.fixUrl
import me.rerere.awara.ui.component.common.BackButton
import me.rerere.awara.ui.component.player.Player
import me.rerere.awara.ui.component.player.PlayerScaffold
import me.rerere.awara.ui.component.player.PlayerState
import me.rerere.awara.ui.component.player.rememberPlayerState
import org.koin.androidx.compose.koinViewModel

@Composable
fun VideoPage(vm: VideoVM = koinViewModel()) {
    val state = rememberPlayerState()
    var fullscreen by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        vm.events.collectLatest {
            when (it) {
                is VideoVM.VideoEvent.UrlLoaded -> {
                    state.updatePlayerItems(
                        it.urls.map {
                            PlayerState.PlayerItem(
                                quality = it.name,
                                mediaItem = MediaItem.fromUri(it.src.view.fixUrl())
                            )
                        }
                    )
                    state.updateCurrentQuality(it.urls.lastOrNull()?.name ?: "Unknown")
                    // TODO: Update current quality to user's preference
                }
            }
        }
    }
    PlayerScaffold(
        fullscreen = fullscreen,
        player = {
            Player(
                state = state,
                modifier = Modifier.fillMaxSize(),
                actions = {
                    IconButton(
                        onClick = {
                            fullscreen = !fullscreen
                        }
                    ) {
                        Icon(Icons.Outlined.Fullscreen, "Fullscreen")
                    }

                    Box {
                        var expandedVideoQuality by remember {
                            mutableStateOf(false)
                        }
                        TextButton(
                            onClick = {
                                expandedVideoQuality = true
                            }
                        ) {
                            Text(
                                text = state.currentQuality,
                                color = Color.White
                            )
                        }
                        DropdownMenu(
                            expanded = expandedVideoQuality,
                            onDismissRequest = { expandedVideoQuality = false }
                        ) {
                            vm.state.urls.forEach { url ->
                                DropdownMenuItem(
                                    onClick = {
                                        expandedVideoQuality = false
                                        state.updateCurrentQuality(url.name)
                                        state.prepare()
                                    },
                                    text = {
                                        Text(url.name)
                                    }
                                )
                            }
                        }
                    }
                }
            )
        }
    ) { player ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(vm.state.video?.title ?: "", maxLines = 1)
                    },
                    navigationIcon = {
                        BackButton()
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Box(
                    modifier = Modifier.aspectRatio(16 / 9f)
                ) {
                    player()
                }
            }
        }
    }
}