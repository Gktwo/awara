package me.rerere.awara.ui.hooks

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import me.rerere.awara.util.findActivity

@Composable
fun rememberWindowSizeClass(): WindowSizeClass {
    val context = LocalContext.current
    return calculateWindowSizeClass(
        context.findActivity()
    )
}

