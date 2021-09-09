package ru.krackdigger.simplenote

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.*

class MainViewModel(application: Application): AndroidViewModel(application) {

    var repository: UserRepository
    var allNumbers: LiveData<List<UserEntity>>? = null
    var allPoolNumbers: LiveData<List<PoolEntity>>? = null
    var counter: Int = 0
    var contBool: Boolean = true

    init {
        val userDao = UserDatabase.getInstance(getApplication()).userDao()
        val poolDao = UserDatabase.getInstance(getApplication()).poolDao()
        repository = UserRepository(userDao, poolDao)
        allNumbers = repository.allNumbers
        allPoolNumbers = repository.allPoolNumbers
        onThread()
    }

    fun getListUsers() = allNumbers
    fun getListUsersPool() = allPoolNumbers

    fun insert(num: UserEntity) = viewModelScope.launch {
        repository.insert(num)
    }

    fun insertPool(num: PoolEntity) = viewModelScope.launch {
        repository.insertPool(num)
    }

    fun delete(num: UserEntity) = viewModelScope.launch {
        repository.delete(num)
    }

    fun deletePool(num: PoolEntity) = viewModelScope.launch {
        repository.deletePool(num)
    }

    fun deleteByTitleList(number: Int) = viewModelScope.launch {
        repository.deleteByTitleList(number)
    }

    fun onThread() {

        var number: Int? = null
        try {
            number = this.allNumbers?.value?.last()?.title
            val numberMax: Int = allNumbers?.value?.maxByOrNull { it.title }!!.title
            if (contBool) {
                counter = numberMax + 1
                contBool = false
            }
        } catch (e: Exception) {
        }

        var numberPool: Int? = null
        try {
            numberPool = this.allPoolNumbers?.value?.first()?.title_pool
        } catch (e: Exception) {
        }

        viewModelScope.launch(Dispatchers.Main) {

            if (numberPool != null) {
                insert(UserEntity(numberPool))
                deleteByTitleList(numberPool)
            } else {
                if (number != null) {
                    insert(UserEntity(counter))
                    counter++
                }
            }

            withContext(Dispatchers.IO) {
                Thread.sleep(5000)
            }
            onThread()
        }
    }
}