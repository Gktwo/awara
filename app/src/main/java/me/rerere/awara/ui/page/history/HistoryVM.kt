package me.rerere.awara.ui.page.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kotlinx.coroutines.launch
import me.rerere.awara.di.AppDatabase

class HistoryVM(
    private val database: AppDatabase
) : ViewModel() {
    val historyItems = Pager(
        config = PagingConfig(
            pageSize = 32,
            enablePlaceholders = false,
            initialLoadSize = 32,
            prefetchDistance = 4,
        ),
        pagingSourceFactory = {
            database.historyDao().getHistory()
        }
    ).flow.cachedIn(viewModelScope)

    fun clearAll() {
        viewModelScope.launch {
            database.historyDao().clearAllHistory()
        }
    }
}