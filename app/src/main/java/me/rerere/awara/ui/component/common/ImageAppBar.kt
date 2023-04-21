package me.rerere.awara.ui.component.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ImageAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    titleTextStyle: TextStyle = MaterialTheme.typography.titleLarge,
    image: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    maxHeight: Dp = 152.dp,
    pinnedHeight: Dp = 64.dp,
    scrollBehavior: TopAppBarScrollBehavior?
) {
    if (maxHeight <= pinnedHeight) {
        throw IllegalArgumentException(
            "A TwoRowsTopAppBar max height should be greater than its pinned height"
        )
    }
    val pinnedHeightPx: Float
    val maxHeightPx: Float
    val imageHeightDp: Dp
    val windowInsetsHeight: Dp
    LocalDensity.current.run {
        pinnedHeightPx = pinnedHeight.toPx()
        maxHeightPx = maxHeight.toPx()
        imageHeightDp = (maxHeightPx - pinnedHeightPx + (scrollBehavior?.state?.heightOffset
            ?: 0f)).toDp()
        windowInsetsHeight = windowInsets.getTop(this).toDp()
    }

    SideEffect {
        if (scrollBehavior?.state?.heightOffsetLimit != pinnedHeightPx - maxHeightPx) {
            scrollBehavior?.state?.heightOffsetLimit = pinnedHeightPx - maxHeightPx
        }
    }

    val colorTransitionFraction = scrollBehavior?.state?.collapsedFraction ?: 0f


    Surface(
        modifier = modifier,
    ) {
        Box {
            Box(
                modifier = Modifier
                    .alpha(1f - colorTransitionFraction.coerceAtMost(1f))
                    .height(imageHeightDp + pinnedHeight + windowInsetsHeight)
                    .fillMaxWidth()
            ) {
                image()
            }


            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .windowInsetsPadding(windowInsets)
                    .clipToBounds()
                    .fillMaxWidth()
                    .height(pinnedHeight),
            ) {
                navigationIcon()
                ProvideTextStyle(titleTextStyle) {
                    title()
                }
                Spacer(modifier = Modifier.weight(1f))
                actions()
            }
        }
    }
}