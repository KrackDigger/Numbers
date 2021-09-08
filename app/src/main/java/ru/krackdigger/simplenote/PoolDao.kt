package ru.krackdigger.simplenote

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PoolDao {

    @Query("SELECT * FROM pool_number_items")
    fun getAllPool(): LiveData<List<PoolEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPool(vararg numbers: PoolEntity)

    @Delete
    suspend fun deletePool(number: PoolEntity)

    @Query("DELETE from pool_number_items WHERE title_pool = :number")
    suspend fun deleteByTitleList(number: Int)

    @Update
    suspend fun updatePool(vararg numbers: PoolEntity)
}