package me.rerere.awara.ui.hooks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun <P> rememberDebounce(
    delay: Long,
    action: (P) -> Unit
) = remember(delay) {
    Debounce(delay, action)
}

class Debounce<T>(
    private val delay: Long,
    private val action: (T) -> Unit
) {
    private var lastTime = 0L

    operator fun invoke(param: T) {
        val now = System.currentTimeMillis()
        if (now - lastTime > delay) {
            action(param).also {
                lastTime = now
            }
        }
    }
}