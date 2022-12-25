package com.example.restaurants.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestaurantRetrofitClient {

    private const val baseUrl = "https://www.triposo.com/"

    fun getInstance() : Retrofit{
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
