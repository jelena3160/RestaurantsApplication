package com.example.restaurants.viewModel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurants.data.*
import kotlinx.coroutines.launch

class AboutViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }
    private val sharedPref: SharedPreferences = application.getSharedPreferences("UserInfoPref", Context.MODE_PRIVATE)
    fun getName(): String? {
        return sharedPref.getString("usrName", "")
    }

    fun getLastName(): String? {
        return sharedPref.getString("usrLastName", "")
    }

    fun getFoodPreference(): String? {
        return sharedPref.getString("foodPreference", "")
    }

    fun getEmail(): String? {
        return sharedPref.getString("usrEmail", "")
    }

    fun clearData(){
        sharedPref.edit().clear().apply()
    }

    fun setName(name: String) {
        sharedPref.edit().putString("usrName", name).apply()
    }

    fun setLastName(lastName: String){
        sharedPref.edit().putString("usrLastName", lastName).apply()
    }

    fun setFoodPreference(foodPreference: String){
        sharedPref.edit().putString("foodPreference", foodPreference).apply()
    }

    fun updateName(userName: UserName){
        viewModelScope.launch {
            repository.updateName(userName)
        }
    }

    fun updateLastName(userLastName: UserLastName){
        viewModelScope.launch {
            repository.updateLastName(userLastName)
        }
    }

    fun updateFoodPreference(foodPreference: FoodPreference){
        viewModelScope.launch {
            repository.updateFoodPreference(foodPreference)
        }
    }






}