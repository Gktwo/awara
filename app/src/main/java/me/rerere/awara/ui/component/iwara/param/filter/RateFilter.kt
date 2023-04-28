package me.rerere.awara.ui.component.iwara.param.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.rerere.awara.ui.component.iwara.param.FilterValue

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RatingFilter(
    values: List<FilterValue>,
    onValueAdd: (FilterValue) -> Unit,
    onValueRemove: (FilterValue) -> Unit
) {
    val currentValue: FilterValue? by remember {
        derivedStateOf { values.firstOrNull { it.key == "rating" } }
    }
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        FilterChip(
            selected = currentValue?.value == "all",
            label = {
                Text(stringResource(id = me.rerere.awara.R.string.rating_all))
            },
            onClick = {
                if (currentValue?.value == "all") {
                    onValueRemove(currentValue!!)
                } else {
                    currentValue?.let(onValueRemove)
                    onValueAdd(FilterValue("rating", "all"))
                }
            },
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.primary,
                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
            )
        )

        FilterChip(
            selected = currentValue?.value == "general",
            label = {
                Text(stringResource(id = me.rerere.awara.R.string.rating_general))
            },
            onClick = {
                if (currentValue?.value == "general") {
                    onValueRemove(currentValue!!)
                } else {
                    currentValue?.let(onValueRemove)
                    onValueAdd(FilterValue("rating", "general"))
                }
            },
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.primary,
                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
            )
        )

        FilterChip(
            selected = currentValue?.value == "ecchi",
            label = {
                Text(stringResource(id = me.rerere.awara.R.string.rating_ecchi))
            },
            onClick = {
                if (currentValue?.value == "ecchi") {
                    onValueRemove(currentValue!!)
                } else {
                    currentValue?.let(onValueRemove)
                    onValueAdd(FilterValue("rating", "ecchi"))
                }
            },
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.primary,
                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
}