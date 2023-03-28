package me.rerere.awara.ui.component.player

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PlayerScaffold(
    fullscreen: Boolean,
    player: @Composable () -> Unit,
    content: @Composable (@Composable () -> Unit) -> Unit
) {
    if (fullscreen) {
        Box(modifier = Modifier.fillMaxSize()) {
            player()
        }
    } else {
        content(player)
    }
}