package me.rerere.awara.ui.component.iwara.param

data class FilterValue(
    val key: String,
    val value: String
)

fun List<FilterValue>.toParams(): Map<String, String> {
    return groupBy { it.key }.map { (key, value) ->
        key to value.joinToString(separator = "%2C") { it.value }
    }.toMap()
}