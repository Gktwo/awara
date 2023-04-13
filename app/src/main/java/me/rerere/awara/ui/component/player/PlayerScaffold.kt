package me.rerere.awara.ui.component.player

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.rerere.awara.ui.hooks.rememberFullScreenState

@Composable
fun PlayerScaffold(
    fullscreen: Boolean,
    player: @Composable () -> Unit,
    content: @Composable (@Composable () -> Unit) -> Unit
) {
    rememberFullScreenState().apply {
        if (fullscreen) enterFullScreen() else exitFullScreen()
    }

    if (fullscreen) {
        BoxWithConstraints(
            modifier = Modifier
            .fillMaxSize()
        ) {
            player()
        }
    } else {
        content(player)
    }
}