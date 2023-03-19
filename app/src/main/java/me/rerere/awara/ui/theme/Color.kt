package me.rerere.awara.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val ColorScheme.warning: Color
    @Composable
    get() = if(!isSystemInDarkTheme()) Color(0xFFFF7D00) else Color(0xFFFF9626)

val ColorScheme.info: Color
    @Composable
    get() = if(!isSystemInDarkTheme()) Color(0xFF165DFF) else Color(0xFF3C7EFF)

val ColorScheme.success: Color
    @Composable
    get() = if(!isSystemInDarkTheme()) Color(0xFF00C853) else Color(0xFF00E676)
