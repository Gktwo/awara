package me.rerere.awara.ui.page.index.pager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.rerere.awara.ui.component.common.Spin
import me.rerere.awara.ui.component.ext.DynamicStaggeredGridCells
import me.rerere.awara.ui.component.iwara.MediaCard
import me.rerere.awara.ui.component.iwara.PaginationBar
import me.rerere.awara.ui.hooks.rememberDebounce
import me.rerere.awara.ui.page.index.IndexVM

@Composable
fun IndexSubscriptionPage(
    vm: IndexVM
) {
    Column {
        Spin(
            show = vm.state.subscriptionLoading,
            modifier = Modifier.weight(1f),
        ) {
            LazyVerticalStaggeredGrid(
                modifier = Modifier.fillMaxSize(),
                columns = DynamicStaggeredGridCells(150.dp, 2, 4),
                verticalItemSpacing = 8.dp,
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(vm.state.subscriptions) {
                    MediaCard(media = it)
                }
            }
        }
        val pageJumpDebounce = rememberDebounce(delay = 500L) { page: Int ->
            vm.jumpToSubscriptionPage(page)
        }
        PaginationBar(
            page = vm.state.subscriptionPage,
            onPageChange = {
                pageJumpDebounce(it)
            },
            leading = {
                var showDropdown by remember {
                    mutableStateOf(false)
                }
                FilledTonalButton(
                    onClick = { showDropdown = true }
                ) {
                    Text(stringResource(vm.state.subscriptionType.id))
                }
                DropdownMenu(
                    expanded = showDropdown,
                    onDismissRequest = { showDropdown = false }
                ) {
                    IndexVM.SubscriptionType.values().forEach {
                        DropdownMenuItem(
                            text = {
                                Text(stringResource(it.id))
                            },
                            onClick = {
                                vm.changeSubscriptionType(it)
                                showDropdown = false
                            },
                        )
                    }
                }
            }
        )
    }
}