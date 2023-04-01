package me.rerere.awara.ui.component.iwara.param.filter

import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import me.rerere.awara.ui.component.iwara.param.Filter
import me.rerere.awara.ui.component.iwara.param.FilterOption
import me.rerere.awara.ui.component.iwara.param.FilterType

val RateFilter = Filter(
    key = "rating",
    label = {
        Text(stringResource(id = me.rerere.awara.R.string.rate))
    },
    type = FilterType.SINGLE,
    options = listOf(
        FilterOption(
            value = "all",
            label = {
                Text(stringResource(id = me.rerere.awara.R.string.rate_all))
            }
        ),
        FilterOption(
            value = "general",
            label = {
                Text(stringResource(id = me.rerere.awara.R.string.rate_general))
            }
        ),
        FilterOption(
            value = "ecchi",
            label = {
                Text(stringResource(id = me.rerere.awara.R.string.rate_ecchi))
            }
        ),
    )
)