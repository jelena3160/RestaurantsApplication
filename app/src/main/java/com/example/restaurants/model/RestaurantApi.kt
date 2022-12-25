package com.example.restaurants.model

import com.example.restaurants.model.json.RestaurantsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface RestaurantApi {
    @Headers(
        "X-Triposo-Account: E9CHL2VB",
        "X-Triposo-Token: 81vio7opy5th0q8z5b3ch7whyophexp0"
    )
    @GET("api/20220411/poi.json?location_id=Paris&tag_labels=cuisine")
    suspend fun getData(): Response<RestaurantsResponse>
}