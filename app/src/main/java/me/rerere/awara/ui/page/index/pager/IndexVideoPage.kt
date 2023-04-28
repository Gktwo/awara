package me.rerere.awara.ui.page.index.pager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.rerere.awara.ui.component.common.Spin
import me.rerere.awara.ui.component.ext.DynamicStaggeredGridCells
import me.rerere.awara.ui.component.iwara.MediaCard
import me.rerere.awara.ui.component.iwara.PaginationBar
import me.rerere.awara.ui.component.iwara.param.FilterAndSort
import me.rerere.awara.ui.component.iwara.param.sort.MediaSortOptions
import me.rerere.awara.ui.page.index.IndexVM

@Composable
fun IndexVideoPage(vm: IndexVM) {
    val state = vm.state
    Column {
        Spin(
            show = state.videoLoading,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            LazyVerticalStaggeredGrid(
                columns = DynamicStaggeredGridCells(150.dp, 2, 4),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalItemSpacing = 8.dp,
                modifier = Modifier.matchParentSize()
            ) {
                items(state.videoList) {
                    MediaCard(media = it)
                }
            }
        }

        PaginationBar(
            page = state.videoPage,
            limit = 24,
            total = state.videoCount,
            onPageChange = {
                vm.updateVideoPage(it)
            },
            leading = {
                FilterAndSort(
                    sort = vm.videoSort,
                    onSortChange = {
                        vm.updateVideoSort(it)
                    },
                    sortOptions = MediaSortOptions,
                    filterValues = vm.videoFilters,
                    onFilterAdd = vm::addVideoFilter,
                    onFilterRemove = vm::removeVideoFilter,
                    onFilterChooseDone = {
                        vm.loadVideoList()
                    },
                    onFilterClear = {
                        vm.clearVideoFilter()
                    }
                )
            }
        )
    }
}