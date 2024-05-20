package com.midterm.foododeringapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.midterm.foododeringapp.Model.CartItem
import com.midterm.foododeringapp.Model.FoodModel
import com.midterm.foododeringapp.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private var binding: ActivityDetailsBinding? = null
    private var foodName: String? = null
    private var foodPrize: String? = null
    private var foodDescription: String? = null
    private var foodImage: String? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var item: FoodModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        auth = FirebaseAuth.getInstance()

        item = intent.getParcelableExtra<FoodModel>("food")!!


        binding?.tvNameFood?.text = item?.foodName
        binding?.tvDescription?.text = item?.foodDescription
        binding?.tvIngredients?.text = item?.foodIngredient
        Log.d("food",""+ item?.foodIngredient)
        val uri = Uri.parse(item?.foodImage)
        Glide.with(this).load(uri).into(binding?.imgFood!!)
        binding?.btnBack?.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding?.btnAddToCard?.setOnClickListener {
            addItemToCart()
        }
    }

    private fun addItemToCart() {
        val database = FirebaseDatabase.getInstance().reference
        var userId = auth.currentUser?.uid?:""
        //Create a cartItems object
        val cartItem = CartItem(item.foodName,item.foodPrice,item.foodImage,item.foodDescription,1,item.foodIngredient)
        //Save data to cart item to database
        database.child("user").child(userId).child("CartItems").push().setValue(cartItem).addOnSuccessListener {
            Toast.makeText(this,"Add item to cart successfully!", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this,"Item not added", Toast.LENGTH_SHORT).show()
        }
    }
}