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





}