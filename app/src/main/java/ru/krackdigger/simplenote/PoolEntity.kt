package ru.krackdigger.simplenote

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pool_number_items")
data class PoolEntity(
        @ColumnInfo(name = "title_pool") var title_pool: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}