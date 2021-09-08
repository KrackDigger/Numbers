package ru.krackdigger.simplenote

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM number_items")
    fun getAll(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg numbers: UserEntity)

    @Delete
    suspend fun delete(number: UserEntity)

    @Update
    suspend fun update(vararg numbers: UserEntity)
}