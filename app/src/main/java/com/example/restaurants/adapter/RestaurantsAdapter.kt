package com.example.restaurants.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurants.R
import com.example.restaurants.model.json.Results
import com.google.android.material.imageview.ShapeableImageView

class RestaurantsAdapter(private var dataset: ArrayList<Results>): RecyclerView.Adapter<RestaurantsAdapter.ViewHolder>(){


    private var bundle: Bundle =Bundle()
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val restaurantName: TextView
        val location:TextView
        init{
            location=view.findViewById(R.id.tvRestaurantAddress)
            restaurantName=view.findViewById(R.id.tvRestaurantName)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.location.text=dataset[position].locationId
        holder.restaurantName.text=dataset[position].name
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateUserList(datasetNew: ArrayList<Results>) {
        dataset.clear()
        dataset = datasetNew

        notifyDataSetChanged()
    }
}

