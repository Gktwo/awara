package me.rerere.awara.ui.component.iwara.param

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import me.rerere.awara.R

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
    onFilterFinish: () -> Unit = {},
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
                Text(stringResource(R.string.filters))
            },
            text = {
                val pagerState = rememberPagerState()
                val scope = rememberCoroutineScope()
                Column {
                    ScrollableTabRow(
                        selectedTabIndex = pagerState.currentPage,
                        edgePadding = 4.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        filterOptions.forEachIndexed { index, filter ->
                            Tab(
                                selected = index == pagerState.currentPage,
                                onClick = {
                                    scope.launch { pagerState.animateScrollToPage(index) }
                                },
                                modifier = Modifier.height(40.dp)
                            ) {
                                filter.label()
                            }
                        }
                    }
                    HorizontalPager(
                        pageCount = filterOptions.size,
                        state = pagerState,
                    ) {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val filter = filterOptions[it]
                            filter.options.forEach { option ->
                                val selected = filters.any { value ->
                                    value.key == filter.key && value.value == option.value
                                }
                                FilterChip(
                                    selected = selected,
                                    onClick = {
                                        when (filter.type) {
                                            FilterType.SINGLE -> {
                                                filters.filter { value ->
                                                    value.key == filter.key
                                                }.forEach { value ->
                                                    onFilterRemove(value)
                                                }
                                                if (!selected) {
                                                    onFilterAdd(
                                                        FilterValue(
                                                            key = filter.key,
                                                            value = option.value
                                                        )
                                                    )
                                                }
                                            }

                                            FilterType.MULTI -> {
                                                if (selected) {
                                                    onFilterRemove(
                                                        FilterValue(
                                                            key = filter.key,
                                                            value = option.value
                                                        )
                                                    )
                                                } else {
                                                    onFilterAdd(
                                                        FilterValue(
                                                            key = filter.key,
                                                            value = option.value
                                                        )
                                                    )
                                                }
                                            }
                                        }
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
                TextButton(onClick = {
                    onFilterFinish()
                    showFilterModal = false
                }) {
                    Text(stringResource(R.string.confirm))
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
