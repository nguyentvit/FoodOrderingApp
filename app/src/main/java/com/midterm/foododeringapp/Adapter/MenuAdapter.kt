package com.midterm.foododeringapp.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.midterm.foododeringapp.DetailsActivity
import com.midterm.foododeringapp.Model.FoodModel
import com.midterm.foododeringapp.databinding.MenuItemBinding
import com.midterm.foododeringapp.setGradientTextColor

class MenuAdapter(private val items: ArrayList<FoodModel>,
                    private var requiredContext: Context
) :RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    private val itemClickListener: OnClickListener ?= null

    inner class MenuViewHolder(private val binding: MenuItemBinding) :RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    itemClickListener.onItemClick(position)

                }

            }
        }
        val tvNameFood = binding.tvNameFood
        val tvNameRest = binding.tvNameRest
        val tvPrice = binding.tvPrice
        val image = binding.imgFood
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(MenuItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val model: FoodModel = items[position]
        holder.tvNameFood.text = model.getNameFood()
        holder.tvPrice.setGradientTextColor(
            Color.parseColor("#E85353"),
            Color.parseColor("#BE1515")
        )

        holder.tvPrice.text = model.getPrice()
        holder.tvNameRest.text = model.getNameRest()
        holder.image.setImageResource(model.getImage())
        holder.tvNameFood.setOnClickListener{
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

private fun OnClickListener?.onItemClick(position: Int) {

}
