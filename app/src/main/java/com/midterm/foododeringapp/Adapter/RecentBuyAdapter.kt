package com.midterm.foododeringapp.Adapter

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.bumptech.glide.Glide
import com.midterm.foododeringapp.Model.OrderDetails
import com.midterm.foododeringapp.databinding.RecentBuyItemBinding
import com.midterm.foododeringapp.setGradientTextColor

class RecentBuyAdapter (private var context: Context,
    private var foodNames: ArrayList<String>,
                        private var foodImages: ArrayList<String>,
                        private var foodPrices: ArrayList<String>,
                        private var foodQuantity: ArrayList<String>): RecyclerView.Adapter<RecentBuyAdapter.ViewHolder>(){
    class ViewHolder (private var binding: RecentBuyItemBinding): RecyclerView.ViewHolder(binding.root){
        val nameFood = binding.tvNameFood
        val quantity = binding.tvCount
        val tvPrice = binding.tvPrice
        val imageFood = binding.imgFood

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecentBuyItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return foodNames.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameFood.text = foodNames[position]
        holder.quantity.text = foodQuantity[position]
        holder.tvPrice.text = foodPrices[position]
        val uri = Uri.parse(foodImages[position])
        Glide.with(context).load(uri).into(holder.imageFood)
        holder.tvPrice.setGradientTextColor(
            Color.parseColor("#E85353"),
            Color.parseColor("#BE1515")
        )
        holder.quantity.setGradientTextColor(
            Color.parseColor("#E85353"),
            Color.parseColor("#BE1515")
        )
    }

}