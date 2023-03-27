package me.rerere.awara.ui.component.common

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import me.rerere.awara.ui.theme.skeleton

@Composable
fun Skeleton(
    modifier: Modifier = Modifier,
) {
    val color = MaterialTheme.colorScheme.skeleton
    val transition = rememberInfiniteTransition(label = "skeleton")
    val translateAnimation by transition.animateColor(
        initialValue = color.startColor,
        targetValue = color.endColor,
        animationSpec = infiniteRepeatable(
            animation = tween(600),
            repeatMode = RepeatMode.Reverse
        ),
        label = "skeleton"
    )
    Box(
        modifier = modifier
            .background(translateAnimation)
    )
}

@Composable
fun SkeletonBox(
    modifier: Modifier = Modifier,
    show: Boolean,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        content()
        if (show) {
            Skeleton(modifier = Modifier.matchParentSize())
        }
    }
}