package com.example.restaurants.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurants.data.UserDatabase
import com.example.restaurants.data.UserPassword
import com.example.restaurants.data.UserRepository
import kotlinx.coroutines.launch

class ResetPasswordViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    fun updatePassword(password: UserPassword){
        viewModelScope.launch {
            repository.updatePassword(password)
        }
    }
}