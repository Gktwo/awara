package me.rerere.awara.ui.component.player

import android.view.SurfaceView
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView

private const val TAG = "PlayerBase"

// 纯粹的播放器组件，无UI附加
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun PlayerBase(
    modifier: Modifier = Modifier,
    state: PlayerState,
) {
    AndroidView(
        factory = {
            PlayerView(it).apply {
                layoutParams = android.view.ViewGroup.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT
                )
                keepScreenOn = true
                player = state.player
                resizeMode = state.resizeMode
                useController = false
                clipToOutline = true
            }
        },
        update = {
            it.resizeMode = state.resizeMode
        },
        modifier = modifier.background(Color.Black)
    )
}