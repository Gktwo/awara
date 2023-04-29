package me.rerere.awara.ui.theme

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
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
import com.google.android.material.color.utilities.QuantizerCelebi
import com.google.android.material.color.utilities.Scheme
import com.google.android.material.color.utilities.Score
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.rerere.awara.util.await
import me.rerere.compose_setting.preference.mmkvPreference
import me.rerere.compose_setting.preference.rememberBooleanPreference
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

private const val TAG = "DynamicTheme"

@Composable
fun DynamicColorTheme(
    state: DynamicThemeState,
    content: @Composable () -> Unit
) {
    val dynamicColor by rememberBooleanPreference(
        key = "setting.dynamic_color",
        default = true
    )
    val darkMode = LocalDarkMode.current
    val scheme = if (dynamicColor) {
        if (darkMode) state.darkScheme else state.lightScheme
    } else null
    MaterialTheme(
        colorScheme = scheme ?: MaterialTheme.colorScheme,
    ) {
        content()
    }
}

@Stable
@SuppressLint("RestrictedApi")
class DynamicThemeState : KoinComponent {
    var lightScheme by mutableStateOf<ColorScheme?>(null)
        private set

    var darkScheme by mutableStateOf<ColorScheme?>(null)
        private set

    suspend fun updateColorScheme(
        url: String
    ) {
        if (url.isEmpty()) return
        if(mmkvPreference.getBoolean("setting.dynamic_color", true).not()) return
        Log.i(TAG, "updateColorScheme: 开始设置 $url")
        withContext(Dispatchers.IO) {
            kotlin.runCatching {
                val request = Request.Builder()
                    .url(url)
                    .get()
                    .build()
                val response = get<OkHttpClient>().newCall(request).await()
                if (response.isSuccessful) {
                    val bitmap = BitmapFactory.decodeStream(response.body?.byteStream())

                    val width = bitmap.width
                    val height = bitmap.height
                    val pixels = IntArray(width * height)
                    bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

                    val seedColor = Score.score(QuantizerCelebi.quantize(pixels, 128))[0]
                    lightScheme = Scheme.light(seedColor).toColorScheme()
                    darkScheme = Scheme.dark(seedColor).toColorScheme()

                    bitmap.recycle()

                    Log.i(TAG, "updateColorScheme: 设置完成 $seedColor ${bitmap.config}")
                }
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