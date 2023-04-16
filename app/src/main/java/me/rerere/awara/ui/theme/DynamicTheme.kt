package me.rerere.awara.ui.theme

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import coil.ImageLoader
import coil.imageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.google.android.material.color.utilities.QuantizerCelebi
import com.google.android.material.color.utilities.Scheme
import com.google.android.material.color.utilities.Score
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val TAG = "DynamicTheme"

@Composable
fun DynamicColorTheme(
    state: DynamicThemeState,
    content: @Composable () -> Unit
) {
    val scheme = if (LocalDarkMode.current) {
        state.darkScheme ?: MaterialTheme.colorScheme
    } else {
        state.lightScheme ?: MaterialTheme.colorScheme
    }
    MaterialTheme(
        colorScheme = scheme,
    ) {
        content()
    }
}

@Stable
@SuppressLint("RestrictedApi")
class DynamicThemeState {
    var lightScheme by mutableStateOf<ColorScheme?>(null)
        private set

    var darkScheme by mutableStateOf<ColorScheme?>(null)
        private set

    suspend fun updateColorScheme(
        ctx: Context,
        url: String
    ) {
        if (url.isEmpty()) return
        Log.i(TAG, "updateColorScheme: 开始设置 $url")
        withContext(Dispatchers.IO) {
            val request = ImageRequest.Builder(ctx)
                .data(url)
                .allowHardware(false)
                .build()

            val result = ctx.imageLoader.execute(request)
            if(result is SuccessResult) {
                val bitmap = (result.drawable as BitmapDrawable).bitmap

                val width = bitmap.width
                val height = bitmap.height
                val pixels = IntArray(width * height)
                bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

                val seedColor = Score.score(QuantizerCelebi.quantize(pixels, 128))[0]
                lightScheme = Scheme.light(seedColor).toColorScheme()
                darkScheme = Scheme.dark(seedColor).toColorScheme()

                Log.i(TAG, "updateColorScheme: 设置完成 $seedColor")
            }
        }
    }

    private fun Scheme.toColorScheme(): ColorScheme {
        return ColorScheme(
            primary = Color(primary),
            onPrimary = Color(onPrimary),
            primaryContainer = Color(primaryContainer),
            onPrimaryContainer = Color(onPrimaryContainer),
            inversePrimary = Color(inversePrimary),
            secondary = Color(secondary),
            onSecondary = Color(onSecondary),
            secondaryContainer = Color(secondaryContainer),
            onSecondaryContainer = Color(onSecondaryContainer),
            tertiary = Color(tertiary),
            onTertiary = Color(onTertiary),
            tertiaryContainer = Color(tertiaryContainer),
            onTertiaryContainer = Color(onTertiaryContainer),
            background = Color(background),
            onBackground = Color(onBackground),
            surface = Color(surface),
            onSurface = Color(onSurface),
            surfaceVariant = Color(surfaceVariant),
            onSurfaceVariant = Color(onSurfaceVariant),
            surfaceTint = Color(primary),
            inverseSurface = Color(inverseSurface),
            inverseOnSurface = Color(inverseOnSurface),
            error = Color(error),
            onError = Color(onError),
            errorContainer = Color(errorContainer),
            onErrorContainer = Color(onErrorContainer),
            outline = Color(outline),
            outlineVariant = Color(outlineVariant),
            scrim = Color(scrim),
        )
    }
}

@Composable
fun rememberDynamicColorSchemeState() = remember { DynamicThemeState() }