package me.rerere.awara.ui.store

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun ProvideStates(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider {
        content()
    }
}