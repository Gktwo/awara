package me.rerere.awara.ui.hooks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import me.rerere.awara.util.findActivity

@Composable
fun rememberFullScreenState(): FullScreenState {
    val context = LocalContext.current
    val activity = context.findActivity()
    val window = activity.window
    val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
    val state = remember {
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        FullScreenState(windowInsetsController)
    }
    DisposableEffect(state) {
        onDispose {
            windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
        }
    }
    return state
}

class FullScreenState(
    private val windowInsetsController: WindowInsetsControllerCompat
) {
    var isFullScreen by mutableStateOf(false)

    fun enterFullScreen() {
        isFullScreen = true
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

    fun exitFullScreen() {
        isFullScreen = false
        windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
    }
}