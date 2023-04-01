package me.rerere.awara.ui.hooks

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private const val TAG = "SystemBarColor"

@Composable
fun ForceSystemBarColor(appearanceLight: Boolean) {
    val view = LocalView.current
    val window = (view.context as Activity).window
    val initialStatusBarLight = remember {
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars
    }
    DisposableEffect(Unit) {
        WindowCompat.getInsetsController(window, view).apply {
            isAppearanceLightStatusBars = appearanceLight
            isAppearanceLightNavigationBars = appearanceLight
        }
        onDispose {
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = initialStatusBarLight
                isAppearanceLightNavigationBars = initialStatusBarLight
            }
        }
    }
}