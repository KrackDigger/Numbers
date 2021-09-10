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
        onAddItem()
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

    fun onAddItem() {

        val numberPool = allPoolNumbers?.value?.firstOrNull()?.title_pool
        val number = allNumbers?.value?.lastOrNull()?.title
        val numberMax = allNumbers?.value?.maxByOrNull { it.title }?.title
        val numberMaxPool = allPoolNumbers?.value?.maxByOrNull { it.title_pool }?.title_pool

            if (contBool && numberMax != null) {

                    if (numberMaxPool != null) {

                        if (numberMaxPool > numberMax) {
                            counter = numberMaxPool + 1
                        } else counter = numberMax + 1

                    } else counter = numberMax + 1

                contBool = false
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
                delay(5000)
            }
            onAddItem()
        }
    }
}