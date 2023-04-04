package me.rerere.awara.ui.component.iwara.param.filter

import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import me.rerere.awara.R
import me.rerere.awara.ui.component.iwara.param.Filter
import me.rerere.awara.ui.component.iwara.param.FilterOption
import me.rerere.awara.ui.component.iwara.param.FilterType

val DateFilter = Filter(
    key = "date",
    label = {
        Text(stringResource(R.string.date))
    },
    type = FilterType.SINGLE,
    options = listOf(
        FilterOption(
            value = "2023",
            label = {
                Text(text = "2023")
            }
        ),
        FilterOption(
            value = "2022",
            label = {
                Text(text = "2022")
            }
        ),
        FilterOption(
            value = "2021",
            label = {
                Text(text = "2021")
            }
        ),
    )
)