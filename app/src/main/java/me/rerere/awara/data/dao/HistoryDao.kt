package me.rerere.awara.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import me.rerere.awara.data.entity.HistoryItem

@Dao
interface HistoryDao {
    @Query("select * from history_items order by time desc")
    fun getHistory(): PagingSource<Int, HistoryItem>

    @Insert
    suspend fun insertHistory(historyItem: HistoryItem)

    @Delete
    suspend fun deleteHistory(historyItem: HistoryItem)

    @Query("delete from history_items")
    suspend fun clearAllHistory()
}