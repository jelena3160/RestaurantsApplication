package com.example.restaurants.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM users_table")
    fun readAllData(): Flow<List<User>>

    @Query("SELECT * FROM users_table WHERE email LIKE :email AND password LIKE :password LIMIT 1")
    fun getUser(email: String, password: String): Flow<User?>

}