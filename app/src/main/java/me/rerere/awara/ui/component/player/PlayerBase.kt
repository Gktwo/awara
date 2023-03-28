package me.rerere.awara.ui.component.player

import android.view.SurfaceView
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView

private const val TAG = "PlayerBase"

// 纯粹的播放器组件，无UI附加
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun  PlayerBase(
    modifier: Modifier = Modifier,
    state: PlayerState,
) {
    AndroidView(
        factory = {
            SurfaceView(it).apply {
                layoutParams = android.view.ViewGroup.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT
                )
                state.controller?.setVideoSurfaceView(this)
            }
        },
        update = {
            state.controller?.setVideoSurfaceView(it)
        },
        modifier = modifier.background(Color.Black)
    )
}


