package me.rerere.awara.ui.component.iwara

import androidx.compose.runtime.Composable
import me.rerere.awara.ui.stores.LocalUserStore
import me.rerere.awara.ui.stores.collectAsState

@Composable
inline fun OnlyLoginVisible(content: @Composable () -> Unit) {
    val userState = LocalUserStore.current.collectAsState()
    if (userState.user != null) {
        content()
    }
}