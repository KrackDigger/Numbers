package ru.krackdigger.simplenote

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "number_items")
data class UserEntity(
        @ColumnInfo(name = "title") var title: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}