package me.rerere.awara.ui.component.iwara

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import me.rerere.awara.ui.LocalDialogProvider

@Composable
fun FilterAndSort(
    modifier: Modifier = Modifier,
    sort: String?,
    onSortChange: (String) -> Unit,
    sortOptions: List<SortOption>,
    filters: List<FilterValue>,
    filterOptions: List<FilterOption>,
) {
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
            FilterButton(
                filters = filters,
                filterOptions = filterOptions
            )
        }
    }
}

class SortOption(
    val name: String,
    val label: @Composable () -> Unit,
    val icon: (@Composable () -> Unit)? = null,
)

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

class FilterOption(
    val name: String,
    val label: @Composable () -> Unit,
    val render: @Composable () -> Unit,
)

class FilterValue(
    val name: String,
    val value: List<String>,
)

@Composable
private fun FilterButton(
    filters: List<FilterValue>,
    onFilterChange: (FilterValue) -> Unit = {},
    filterOptions: List<FilterOption>,
) {
    val dialog = LocalDialogProvider.current
    val scope = rememberCoroutineScope()
    FilledTonalButton(
        onClick = {
            dialog.show {
                val pager = rememberPagerState()
                Column {
                    ScrollableTabRow(
                        selectedTabIndex = pager.currentPage,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        filterOptions.forEachIndexed { index, option ->
                            Tab(
                                text = {
                                    option.label.invoke()
                                },
                                selected = pager.currentPage == index,
                                onClick = {
                                    scope.launch {
                                        pager.animateScrollToPage(index)
                                    }
                                }
                            )
                        }
                    }

                    HorizontalPager(
                        pageCount = filterOptions.size,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            filterOptions[it].render.invoke()
                        }
                    }
                }
            }
        },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Outlined.Sort, "Filter")
        }
    }
}