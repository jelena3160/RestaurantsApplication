package com.example.restaurants.model.json
import com.google.gson.annotations.SerializedName


data class Attribution (

    @SerializedName("url"       ) var url      : String? = null,
    @SerializedName("source_id" ) var sourceId : String? = null

)