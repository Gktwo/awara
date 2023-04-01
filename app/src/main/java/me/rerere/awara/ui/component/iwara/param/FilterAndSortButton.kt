package me.rerere.awara.ui.component.iwara.param

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterAndSort(
    modifier: Modifier = Modifier,
    sort: String?,
    onSortChange: (String) -> Unit,
    sortOptions: List<SortOption>,
    filters: List<FilterValue> = emptyList(),
    onFilterAdd: (FilterValue) -> Unit = {},
    onFilterRemove: (FilterValue) -> Unit = {},
    filterOptions: List<Filter> = emptyList(),
) {
    var showFilterModal by remember {
        mutableStateOf(false)
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (sortOptions.isNotEmpty()) {
            SortButton(
                modifier = modifier,
                sort = sort,
                onSortChange = onSortChange,
                sortOptions = sortOptions
            )
        }

        if (filterOptions.isNotEmpty()) {
            Box {
                FilledTonalButton(
                    onClick = {
                        showFilterModal = true
                    },
                    shape = CircleShape,
                ) {
                    Icon(Icons.Outlined.FilterList, "Filters")
                }

                if (filters.isNotEmpty()) {
                    Badge(
                        modifier = Modifier.align(Alignment.TopEnd),
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    ) {
                        Text(filters.size.toString())
                    }
                }
            }
        }
    }

    if (showFilterModal) {
        AlertDialog(
            onDismissRequest = {
                showFilterModal = false
            },
            title = {
                Text("Filters")
            },
            text = {
                Column {
                    filterOptions.forEach { filter ->
                        ProvideTextStyle(
                            MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        ) {
                            filter.label()
                        }

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            filter.options.forEach { option ->
                                val selected = filters.any { value -> value.key == filter.key && option.value in value.value }
                                FilterChip(
                                    selected = selected,
                                    onClick = {
                                        // TODO
                                    },
                                    label = {
                                        option.label()
                                    },
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { /*TODO*/ }) {
                    Text("确定")
                }
            },
        )
    }
}


@Composable
private fun SortButton(
    modifier: Modifier = Modifier,
    sort: String?,
    onSortChange: (String) -> Unit,
    sortOptions: List<SortOption>,
) {
    var showPopup by remember { mutableStateOf(false) }
    val currentSort = sortOptions.find { it.name == sort } ?: sortOptions.first()
    FilledTonalButton(
        onClick = {
            showPopup = !showPopup
        },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (currentSort.icon != null) {
                currentSort.icon.invoke()
            } else {
                Icon(Icons.Outlined.Sort, "Sort")
            }
            currentSort.label.invoke()
        }
    }
    if (showPopup) {
        DropdownMenu(
            expanded = showPopup,
            onDismissRequest = { showPopup = false }
        ) {
            sortOptions.forEach { option ->
                DropdownMenuItem(
                    text = {
                        option.label.invoke()
                    },
                    onClick = {
                        onSortChange(option.name)
                        showPopup = false
                    },
                    leadingIcon = if (option.icon != null) {
                        {
                            option.icon.invoke()
                        }
                    } else null
                )
            }
        }
    }
}
