package com.example.restaurants.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurants.R
import com.example.restaurants.RestaurantClickHandler
import com.example.restaurants.model.json.Results
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToInt

class HomeAdapter(private var dataset: ArrayList<Results>, private val clickHandler: RestaurantClickHandler): RecyclerView.Adapter<HomeAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.restaurantAddress.text=dataset[position].locationId
        holder.restaurantName.text=dataset[position].name
        Glide.with(holder.restaurantImage.context).load(dataset[position].images[0].sizes?.original?.url).into(holder.restaurantImage)

        if(dataset[position].priceTier == null || dataset[position].priceTier?.toInt()!! >= 1) {
            holder.normal.isVisible = false
            holder.expensive.isVisible = false
        }
        if(dataset[position].priceTier != null && dataset[position].priceTier?.toInt()!! >= 2)
            holder.normal.isVisible = true
        if(dataset[position].priceTier != null && dataset[position].priceTier?.toInt()!! == 3)
            holder.expensive.isVisible = true

        val formatter = DecimalFormat("#.##")
        formatter.roundingMode = RoundingMode.DOWN
        val roundValue = formatter.format(dataset[position].score!!).toDouble()
        val progress = (roundValue.times(10)).roundToInt()
        holder.ratingProgress.progress = progress
        holder.rating.text = roundValue.toString()


    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener{

        val restaurantName: TextView
        val restaurantAddress:TextView
        val restaurantImage: ImageView
        val normal: ImageView
        val expensive: ImageView
        val rating: TextView
        val ratingProgress: ProgressBar
        init{
            restaurantAddress = view.findViewById(R.id.tvRestaurantAddress)
            restaurantName = view.findViewById(R.id.tvRestaurantName)
            restaurantImage = view.findViewById(R.id.iPlaceHolder)
            normal = view.findViewById(R.id.iDollar2)
            expensive = view.findViewById(R.id.iDollar3)
            rating = view.findViewById(R.id.tvRatingProgress)
            ratingProgress = view.findViewById(R.id.progressBar)
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = bindingAdapterPosition
            val currentRestaurant = dataset[position]
            clickHandler.clickedRestaurantItem(currentRestaurant)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateUserList(datasetNew: ArrayList<Results>) {
        dataset.clear()
        dataset = datasetNew

        notifyDataSetChanged()
    }
}

