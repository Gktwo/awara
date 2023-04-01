package me.rerere.awara.ui.component.iwara.param

import androidx.compose.runtime.Composable

enum class FilterType {
    SINGLE, MULTI
}

data class Filter(
    val key: String,
    val label: @Composable () -> Unit,
    val type: FilterType,
    val options: List<FilterOption>
)

data class FilterOption(
    val value: String,
    val label: @Composable () -> Unit,
)

data class FilterValue(
    val key: String,
    val value: List<String>
)

