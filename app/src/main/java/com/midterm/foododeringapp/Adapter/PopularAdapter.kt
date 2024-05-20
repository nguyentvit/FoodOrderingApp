package com.midterm.foododeringapp.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.Image
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.midterm.foododeringapp.DetailsActivity
import com.midterm.foododeringapp.Model.CartItem
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
        val btnAddToCart = binding.ivAddToCart
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val model: FoodModel = items[position]
        holder.tvNameFood.text = model.foodName
        holder.tvPrice.setGradientTextColor(
            Color.parseColor("#E85353"),
            Color.parseColor("#BE1515")
        )

        holder.tvPrice.text = model.foodPrice
       // holder.tvNameRest.text = model.getNameRest()
        val uri = Uri.parse(model.foodImage)
        Glide.with(requiredContext).load(uri).into(holder.image)
        holder.tvNameFood.setOnClickListener {
            val intent = Intent(requiredContext, DetailsActivity::class.java)
            intent.putExtra("food",model)
            requiredContext.startActivity(intent)
        }
        holder.image.setOnClickListener {
            val intent = Intent(requiredContext, DetailsActivity::class.java)
            intent.putExtra("food",model)
            requiredContext.startActivity(intent)
        }
        holder.btnAddToCart.setOnClickListener {
            addItemToCart(model)
        }
    }
    private fun addItemToCart(model: FoodModel) {
        val database = FirebaseDatabase.getInstance().reference
        val auth = FirebaseAuth.getInstance()
        var userId = auth.currentUser?.uid?:""
        //Create a cartItems object
        val cartItem = CartItem(model.foodName,model.foodPrice,model.foodImage,model.foodDescription,1, model.foodIngredient)
        //Save data to cart item to database
        database.child("user").child(userId).child("CartItems").push().setValue(cartItem).addOnSuccessListener {
            Toast.makeText(requiredContext,"Add item to cart successfully!", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(requiredContext,"Item not added", Toast.LENGTH_SHORT).show()
        }
    }
}