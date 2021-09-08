package ru.krackdigger.simplenote

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao, private val poolDao: PoolDao) {

    // UserEntity
    val allNumbers: LiveData<List<UserEntity>> = userDao.getAll()

    suspend fun insert(number: UserEntity) {
        userDao.insert(number)
    }

    suspend fun delete(number: UserEntity) {
        userDao.delete(number)
    }

    // PoolEntity
    val allPoolNumbers: LiveData<List<PoolEntity>> = poolDao.getAllPool()

    suspend fun insertPool(number: PoolEntity) {
        poolDao.insertPool(number)
    }

    suspend fun deletePool(number: PoolEntity) {
        poolDao.deletePool(number)
    }

    suspend fun deleteByTitleList(number: Int) {
        poolDao.deleteByTitleList(number)
    }
}