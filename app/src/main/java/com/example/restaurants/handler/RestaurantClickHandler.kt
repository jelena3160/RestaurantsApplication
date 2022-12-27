package com.example.restaurants.handler

import com.example.restaurants.model.json.Results

interface RestaurantClickHandler {
    fun clickedRestaurantItem(restaurantsResponse: Results)
}