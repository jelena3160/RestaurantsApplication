package com.example.restaurants.model.json
import com.google.gson.annotations.SerializedName


data class Original (

    @SerializedName("url"    ) var url    : String? = null,
    @SerializedName("bytes"  ) var bytes  : Int?    = null,
    @SerializedName("width"  ) var width  : Int?    = null,
    @SerializedName("format" ) var format : String? = null,
    @SerializedName("height" ) var height : Int?    = null

)
