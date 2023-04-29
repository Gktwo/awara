package me.rerere.awara.ui.component.iwara.param

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

data class FilterValue(
    val key: String,
    val value: String
)

fun List<FilterValue>.toParams(): Map<String, String> {
    return groupBy { it.key }.map { (key, value) ->
        key to value.joinToString(separator = ",") { it.value }
    }.toMap()
}

val FilterChipCloseIcon: @Composable (() -> Unit) = {
    Icon(Icons.Outlined.Close, "Close")
}