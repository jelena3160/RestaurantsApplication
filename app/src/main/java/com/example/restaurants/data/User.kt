package com.example.restaurants.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class User(
    @PrimaryKey
    val email: String,
    val firstName: String?,
    val lastName: String?,
    val password: String?,
    val restaurantPreference: String?
)
