package me.rerere.awara.ui.component.ext

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection

@Composable
operator fun PaddingValues.plus(another: PaddingValues) : PaddingValues = PaddingValues(
    start = this.calculateStartPadding(LocalLayoutDirection.current) + another.calculateStartPadding(LocalLayoutDirection.current),
    top = this.calculateTopPadding() + another.calculateTopPadding(),
    bottom = this.calculateBottomPadding() + another.calculateBottomPadding(),
    end = this.calculateEndPadding(LocalLayoutDirection.current) + another.calculateEndPadding(LocalLayoutDirection.current),
)