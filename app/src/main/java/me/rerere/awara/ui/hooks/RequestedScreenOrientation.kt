package me.rerere.awara.ui.hooks

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import me.rerere.awara.util.findActivity

private const val TAG = "ScreenOrientation"

@Composable
fun rememberRequestedScreenOrientation(): MutableState<Int> {
    val activity = LocalContext.current.findActivity() as ComponentActivity
    val initOrientation = remember {
        activity.requestedOrientation
    }
    var state by remember {
        mutableStateOf(initOrientation)
    }
    DisposableEffect(Unit) {
        onDispose {
            activity.requestedOrientation = initOrientation
        }
    }
    return object : MutableState<Int> {
        override var value: Int
            get() = state
            set(value) {
                activity.requestedOrientation = value
                state = value
            }

        override fun component1(): Int = value

        override fun component2(): (Int) -> Unit = { value = it }
    }
}