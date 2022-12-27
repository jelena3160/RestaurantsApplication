package com.example.restaurants.viewModel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.restaurants.model.RestaurantApi
import com.example.restaurants.model.RestaurantRetrofitClient
import com.example.restaurants.model.json.RestaurantsResponse
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    val uiTextLiveData = MutableLiveData<RestaurantsResponse>()
    private val sharedPref: SharedPreferences = application.getSharedPreferences("UserInfoPref", Context.MODE_PRIVATE)

    fun getFoodPreference(): String? {
        return sharedPref.getString("foodPreference", "")
    }
    fun getUpdatedText(location: String, preference: String) {
        viewModelScope.launch{
            val updatedActivity = fetchActivity(location, preference)
            uiTextLiveData.postValue(updatedActivity)
        }
    }

    private suspend fun fetchActivity(location: String,preference: String): RestaurantsResponse {

        val response= RestaurantRetrofitClient.getInstance().create(RestaurantApi::class.java).getFavorites(location,preference)

        if(response.isSuccessful){
            return RestaurantsResponse(response.body()!!.results,response.body()!!.more,response.body()!!.isDeprecated)
        }
        return RestaurantsResponse(response.body()!!.results,response.body()!!.more,response.body()!!.isDeprecated)
    }
}