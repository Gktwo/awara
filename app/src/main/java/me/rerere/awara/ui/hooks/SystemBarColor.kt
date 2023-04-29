package me.rerere.awara.ui.hooks

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import kotlinx.coroutines.delay
import me.rerere.awara.ui.theme.LocalDarkMode

private const val TAG = "SystemBarColor"

@Composable
fun ForceSystemBarColor(appearanceLight: Boolean) {
    val view = LocalView.current
    val window = (view.context as Activity).window
    val darkMode = LocalDarkMode.current
    LaunchedEffect(Unit) {
        delay(350L) // Magic
        WindowCompat.getInsetsController(window, view).apply {
            isAppearanceLightStatusBars = appearanceLight
            isAppearanceLightNavigationBars = appearanceLight
        }
        Log.i(TAG, "ForceSystemBarColor: [init] appearanceLight => $appearanceLight")
    }
    DisposableEffect(Unit) {
        onDispose {
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkMode
                isAppearanceLightNavigationBars = !darkMode
            }
            Log.i(TAG, "ForceSystemBarColor: [dispose] appearanceLight => $darkMode")
        }
    }
}