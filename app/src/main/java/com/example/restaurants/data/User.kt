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

@Entity
class UserName(
    val email: String,
    val firstName: String?
)

@Entity
class UserLastName(
    val email: String,
    val lastName: String?
)

@Entity
class FoodPreference(
    val email: String,
    val restaurantPreference: String?
)
