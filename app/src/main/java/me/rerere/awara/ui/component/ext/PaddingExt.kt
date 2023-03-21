package me.rerere.awara.ui.component.ext

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp

@Composable
operator fun PaddingValues.plus(another: PaddingValues) : PaddingValues = PaddingValues(
    start = this.calculateStartPadding(LocalLayoutDirection.current) + another.calculateStartPadding(LocalLayoutDirection.current),
    top = this.calculateTopPadding() + another.calculateTopPadding(),
    bottom = this.calculateBottomPadding() + another.calculateBottomPadding(),
    end = this.calculateEndPadding(LocalLayoutDirection.current) + another.calculateEndPadding(LocalLayoutDirection.current),
)

@Composable
fun PaddingValues.excludeStart() : PaddingValues = PaddingValues(
    start = 0.dp,
    top = this.calculateTopPadding(),
    bottom = this.calculateBottomPadding(),
    end = this.calculateEndPadding(LocalLayoutDirection.current),
)

@Composable
fun PaddingValues.excludeEnd() : PaddingValues = PaddingValues(
    start = this.calculateStartPadding(LocalLayoutDirection.current),
    top = this.calculateTopPadding(),
    bottom = this.calculateBottomPadding(),
    end = 0.dp,
)

@Composable
fun PaddingValues.excludeTop() : PaddingValues = PaddingValues(
    start = this.calculateStartPadding(LocalLayoutDirection.current),
    top = 0.dp,
    bottom = this.calculateBottomPadding(),
    end = this.calculateEndPadding(LocalLayoutDirection.current),
)

@Composable
fun PaddingValues.excludeBottom() : PaddingValues = PaddingValues(
    start = this.calculateStartPadding(LocalLayoutDirection.current),
    top = this.calculateTopPadding(),
    bottom = 0.dp,
    end = this.calculateEndPadding(LocalLayoutDirection.current),
)
