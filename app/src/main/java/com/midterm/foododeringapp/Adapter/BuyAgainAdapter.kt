package com.midterm.foododeringapp.Adapter

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.midterm.foododeringapp.Model.FoodModel
import com.midterm.foododeringapp.databinding.BuyAgainItemBinding
import com.midterm.foododeringapp.setGradientTextColor

class BuyAgainAdapter(private val buyAgainFoodItems:ArrayList<FoodModel>,
    private val requiredContext: Context
    ):RecyclerView.Adapter<BuyAgainAdapter.HistoryViewHolder>() {
    class HistoryViewHolder(private var binding: BuyAgainItemBinding): RecyclerView.ViewHolder(binding.root) {
        val tvNameFood = binding.tvNameFood
        val tvNameRest = binding.tvNameRest
        val tvPrice = binding.tvPrice
        val image = binding.imageFood
        val btnBuyAgain = binding.btnBuyAgain

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(BuyAgainItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return buyAgainFoodItems.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val model: FoodModel = buyAgainFoodItems[position]
        holder.tvNameFood.text = model.foodName
        holder.tvPrice.setGradientTextColor(
            Color.parseColor("#E85353"),
            Color.parseColor("#BE1515")
        )

        holder.tvPrice.text = model.foodPrice
//        holder.tvNameRest.text = model.getNameRest()
        val uri = Uri.parse(model.foodImage)
        Glide.with(requiredContext).load(uri).into(holder.image)
    }
}