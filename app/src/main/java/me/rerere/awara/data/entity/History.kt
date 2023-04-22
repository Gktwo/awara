package me.rerere.awara.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.time.Instant

@Entity(
    tableName = "history_items"
)
@TypeConverters(HistoryTypeConverter::class, HistoryInstantConverter::class)
data class HistoryItem(
    @PrimaryKey val id: Long,
    @ColumnInfo val time: Instant,
    @ColumnInfo val type: HistoryType,
    @ColumnInfo val resourceId: String,
    @ColumnInfo val title: String,
    @ColumnInfo val description: String,
    @ColumnInfo val thumbnail: String
)

enum class HistoryType {
    VIDEO,
    IMAGE,
    USER,
}

class HistoryTypeConverter {
    @TypeConverter
    fun toHistoryType(value: String) = enumValueOf<HistoryType>(value)

    @TypeConverter
    fun fromHistoryType(value: HistoryType) = value.name
}

class HistoryInstantConverter {
    @TypeConverter
    fun toInstant(value: Long): Instant = Instant.ofEpochSecond(value)

    @TypeConverter
    fun fromInstant(value: Instant): Long = value.epochSecond
}
