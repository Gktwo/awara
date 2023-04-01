package me.rerere.awara.ui.component.iwara.param

import androidx.compose.runtime.Composable

class SortOption(
    val name: String,
    val label: @Composable () -> Unit,
    val icon: (@Composable () -> Unit)? = null,
)