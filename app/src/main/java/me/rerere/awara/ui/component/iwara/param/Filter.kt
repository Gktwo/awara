package me.rerere.awara.ui.component.iwara.param

import androidx.compose.runtime.Composable
import me.rerere.awara.ui.component.iwara.param.FilterType.MULTI
import me.rerere.awara.ui.component.iwara.param.FilterType.SINGLE

/**
 * 过滤器类别
 *
 * @property SINGLE 单选
 * @property MULTI 多选
 */
enum class FilterType {
    SINGLE,
    MULTI
}

/**
 * 过滤器
 *
 * @param key 过滤器的键, 用于构造请求参数
 * @param label 过滤器的标题标签，用于显示过滤器名称
 * @param type 过滤器的类型，用于构造请求参数
 *
 * TODO: 实现自定义渲染和动态加载Options （没想好）
 */
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
    val value: String
)

fun List<FilterValue>.toParams() : Map<String, String> {
    return groupBy { it.key }.map { (key, value) ->
        key to value.joinToString(separator = "%2C") { it.value }
    }.toMap()
}
