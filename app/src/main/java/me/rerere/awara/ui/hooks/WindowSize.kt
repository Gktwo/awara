package me.rerere.awara.ui.hooks

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberWindowSize(): WindowSizeClass {
    val context = LocalContext.current
    return calculateWindowSizeClass(
        context.findActivity()
    )
}

private fun Context.findActivity(): Activity = when (this) {
    is Activity -> this
    is ContextWrapper -> {
        baseContext.findActivity()
    }
    else -> throw IllegalStateException("Context is not an Activity")
}