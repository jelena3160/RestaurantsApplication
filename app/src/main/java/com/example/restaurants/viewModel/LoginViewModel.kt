package com.example.restaurants.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.restaurants.data.User
import com.example.restaurants.data.UserDao
import com.example.restaurants.data.UserDatabase
import com.example.restaurants.data.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository
    private val userDao: UserDao
    val user = MutableLiveData<User>()

    init {
        userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    fun getUserByEmail(email:String, password: String) {
        viewModelScope.launch {
            repository.getUser(email, password).collect{
                user.postValue(it)
            }
        }
    }


}