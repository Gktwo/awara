package me.rerere.awara.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import me.rerere.awara.ui.component.common.Skeleton

val ColorScheme.warning: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Color(0xFFFF7D00) else Color(0xFFFF9626)

val ColorScheme.info: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Color(0xFF165DFF) else Color(0xFF3C7EFF)

val ColorScheme.success: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Color(0xFF00C853) else Color(0xFF00E676)

val ColorScheme.skeleton: SkeletonColor
    @Composable
    get() = if (!isSystemInDarkTheme()) {
        SkeletonColor(
            startColor = Color(0xFFE0E0E0),
            endColor = Color(0xFFD0D0D0)
        )
    }   else {
        // alpha 0.18 ~ 0.12, color white
        // 0.18 = 0xFF * 0.18 = 0x2E
        // 0.12 = 0xFF * 0.12 = 0x1F
        SkeletonColor(
            startColor = Color(0x2EFFFFFF),
            endColor = Color(0x1FFFFFFF)
        )
    }

data class SkeletonColor(
    val startColor: Color,
    val endColor: Color
)