package me.rerere.awara.ui.component.iwara.param.filter

import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import me.rerere.awara.R
import me.rerere.awara.ui.component.iwara.param.Filter
import me.rerere.awara.ui.component.iwara.param.FilterType

val TagFilter = Filter(
    key = "Tag",
    type = FilterType.MULTI,
    label = {
        Text(stringResource(R.string.tag))
    },
    options = emptyList()
)