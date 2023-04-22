package me.rerere.awara.ui.page.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import me.rerere.awara.R
import me.rerere.awara.data.entity.HistoryItem
import me.rerere.awara.ui.component.common.BackButton
import me.rerere.awara.ui.component.ext.plus
import org.koin.androidx.compose.koinViewModel

@Composable
fun HistoryPage(vm: HistoryVM = koinViewModel()) {
    val itemsPaged = vm.historyItems.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(stringResource(R.string.history))
                },
                navigationIcon = {
                    BackButton()
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = it + PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(itemsPaged) { item ->
                if (item != null) {
                    HistoryItem(item)
                }
            }
        }
    }
}

@Composable
private fun HistoryItem(item: HistoryItem) {
    Card {

    }
}

