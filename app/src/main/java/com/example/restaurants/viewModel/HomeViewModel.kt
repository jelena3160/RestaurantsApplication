package com.example.restaurants.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurants.model.RestaurantApi
import com.example.restaurants.model.RestaurantRetrofitClient
import com.example.restaurants.model.json.RestaurantsResponse
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    val uiTextLiveData = MutableLiveData<RestaurantsResponse>()

    fun getUpdatedText() {
        viewModelScope.launch{
            val updatedActivity = fetchActivity()
            uiTextLiveData.postValue(updatedActivity)
        }
    }

    suspend fun fetchActivity(): RestaurantsResponse {

        val instance= RestaurantRetrofitClient.getInstance()
        val api=instance.create(RestaurantApi::class.java)

        val response=api.getData()

        if(response.isSuccessful){
            return RestaurantsResponse(response.body()!!.results,response.body()!!.more,response.body()!!.isDeprecated)
        }
        return RestaurantsResponse(response.body()!!.results,response.body()!!.more,response.body()!!.isDeprecated)
    }
}