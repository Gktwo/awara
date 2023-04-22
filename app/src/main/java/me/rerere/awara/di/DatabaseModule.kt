package me.rerere.awara.di

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.rerere.awara.data.dao.HistoryDao
import me.rerere.awara.data.entity.HistoryItem
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "awara.db"
        ).build()
    }
}

@Database(
    entities = [HistoryItem::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}