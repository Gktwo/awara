package me.rerere.awara.ui.theme

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import me.rerere.compose_setting.preference.rememberIntPreference

private const val TAG = "Theme"

private val DarkColorScheme = darkColorScheme()

private val LightColorScheme = lightColorScheme()

@Composable
fun AwaraTheme(
    content: @Composable () -> Unit
) {
    val darkMode by rememberIntPreference(
        key = "setting.dark_mode",
        default = 0
    )
    val lightMode = when(darkMode) {
        0 -> !isSystemInDarkTheme()
        1 -> true
        2 -> false
        else -> error("Invalid dark mode: $darkMode")
    }
    val colorScheme = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (!lightMode) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        lightMode -> LightColorScheme
        else -> DarkColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        LaunchedEffect(lightMode) {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = lightMode
                isAppearanceLightNavigationBars = lightMode
                Log.i(TAG, "AwaraTheme: isAppearanceLightStatusBars = $isAppearanceLightStatusBars")
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}