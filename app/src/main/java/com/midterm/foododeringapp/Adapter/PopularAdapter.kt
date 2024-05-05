package com.midterm.foododeringapp.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.Image
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.midterm.foododeringapp.DetailsActivity
import com.midterm.foododeringapp.Model.FoodModel
import com.midterm.foododeringapp.databinding.PopularItemBinding
import com.midterm.foododeringapp.setGradientTextColor

class PopularAdapter(private val items: ArrayList<FoodModel>,
    private var requiredContext: Context) : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>(){
    class PopularViewHolder(private val binding: PopularItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvNameFood = binding.tvNameFood
        val tvNameRest = binding.tvNameRest
        val tvPrice = binding.tvPrice
        val image = binding.imageView6

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val model: FoodModel = items[position]
        holder.tvNameFood.text = model.getNameFood()
        holder.tvPrice.setGradientTextColor(
            Color.parseColor("#E85353"),
            Color.parseColor("#BE1515")
        )

        holder.tvPrice.text = model.getPrice()
        holder.tvNameRest.text = model.getNameRest()
        holder.image.setImageResource(model.getImage())
        holder.tvNameFood.setOnClickListener {
            val intent = Intent(requiredContext, DetailsActivity::class.java)
            intent.putExtra("ItemName", items[position].getNameFood())
            intent.putExtra("ItemImage", items[position].getImage())
            requiredContext.startActivity(intent)
        }
        holder.image.setOnClickListener {
            val intent = Intent(requiredContext, DetailsActivity::class.java)
            intent.putExtra("ItemName", items[position].getNameFood())
            intent.putExtra("ItemImage", items[position].getImage())
            requiredContext.startActivity(intent)
        }
    }
}