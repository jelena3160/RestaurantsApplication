package com.example.restaurants

import com.example.restaurants.model.json.Results

interface RestaurantClickHandler {
    fun clickedRestaurantItem(restaurantsResponse: Results)
}