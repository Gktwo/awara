package me.rerere.awara.ui.component.iwara.param

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ClearAll
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material3.Badge
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import me.rerere.awara.ui.component.common.BetterTabBar
import me.rerere.awara.ui.component.iwara.param.filter.DateFilter
import me.rerere.awara.ui.component.iwara.param.filter.RatingFilter
import me.rerere.awara.ui.component.iwara.param.filter.TagFilter

@Composable
fun FilterAndSort(
    modifier: Modifier = Modifier,
    sort: String?,
    onSortChange: (String) -> Unit,
    sortOptions: List<SortOption>,
    filterValues: List<FilterValue>,
    onFilterAdd: (FilterValue) -> Unit,
    onFilterRemove: (FilterValue) -> Unit,
    onFilterChooseDone: () -> Unit,
    onFilterClear: () -> Unit
) {
    var showFilter by remember {
        mutableStateOf(false)
    }
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

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
        Box {
            FilledTonalButton(
                onClick = {
                    showFilter = true
                }
            ) {
                Icon(Icons.Outlined.FilterList, null)
            }

            if (filterValues.isNotEmpty()) {
                Badge(
                    modifier = Modifier.align(Alignment.TopEnd),
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                ) {
                    Text(filterValues.size.toString())
                }
            }
        }
    }

    if (showFilter) {
        ModalBottomSheet(
            onDismissRequest = { showFilter = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier.padding(
                    bottom = 32.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                BetterTabBar(
                    selectedTabIndex = pagerState.currentPage
                ) {
                    Tab(
                        selected = pagerState.currentPage == 0,
                        onClick = { scope.launch { pagerState.animateScrollToPage(0) } },
                        text = {
                            Text(stringResource(R.string.tag))
                        }
                    )
                    Tab(
                        selected = pagerState.currentPage == 1,
                        onClick = { scope.launch { pagerState.animateScrollToPage(1) } },
                        text = {
                            Text(stringResource(R.string.date))
                        }
                    )
                    Tab(
                        selected = pagerState.currentPage == 2,
                        onClick = { scope.launch { pagerState.animateScrollToPage(2) } },
                        text = {
                            Text(stringResource(R.string.rating))
                        }
                    )
                }

                HorizontalPager(
                    pageCount = 3,
                    state = pagerState,
                    modifier = Modifier.height(500.dp)
                ) {
                    when (it) {
                        0 -> {
                            TagFilter(
                                values = filterValues,
                                onValueAdd = onFilterAdd,
                                onValueRemove = onFilterRemove
                            )
                        }

                        1 -> {
                            DateFilter(
                                values = filterValues,
                                onValueAdd = onFilterAdd,
                                onValueRemove = onFilterRemove
                            )
                        }

                        2 -> {
                            RatingFilter(
                                values = filterValues,
                                onValueAdd = onFilterAdd,
                                onValueRemove = onFilterRemove
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.align(Alignment.End),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilledTonalButton(
                        onClick = {
                            onFilterClear()
                        },
                    ) {
                        Icon(Icons.Outlined.ClearAll, null)
                    }

                    FilledTonalButton(
                        onClick = {
                            showFilter = false
                            onFilterChooseDone()
                        },
                    ) {
                        Text(stringResource(id = R.string.confirm))
                    }
                }
            }
        }
    }
}