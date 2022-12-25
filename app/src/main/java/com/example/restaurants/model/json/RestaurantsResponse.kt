package com.example.restaurants.model.json

import com.google.gson.annotations.SerializedName


data class RestaurantsResponse (

    @SerializedName("results"       ) var results      : ArrayList<Results> = arrayListOf(),
    @SerializedName("more"          ) var more         : Boolean?           = null,
    @SerializedName("is_deprecated" ) var isDeprecated : String?            = null

)