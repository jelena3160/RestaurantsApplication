package com.example.restaurants.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    val allUsers: Flow<List<User>> = userDao.readAllData()

    @WorkerThread
    fun getUser(email: String, password: String):Flow<User?> {
        return userDao.getUser(email, password)
    }

    suspend fun updateName(userName: UserName){
        userDao.updateName(userName)
    }

    suspend fun updateLastName(lastName: UserLastName){
        userDao.updateLastName(lastName)
    }

    suspend fun updateFoodPreference(foodPreference: FoodPreference){
        userDao.updateFoodPreference(foodPreference)
    }

    suspend fun updatePassword(password: UserPassword){
        userDao.updatePassword(password)
    }







}