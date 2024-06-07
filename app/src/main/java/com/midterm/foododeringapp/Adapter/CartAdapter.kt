package com.midterm.foododeringapp.Adapter

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.midterm.foododeringapp.CartTotalListener
import com.midterm.foododeringapp.Model.CartItem
import com.midterm.foododeringapp.Model.FoodModel
import com.midterm.foododeringapp.databinding.FoodInCartItemBinding
import com.midterm.foododeringapp.databinding.PopularItemBinding
import com.midterm.foododeringapp.setGradientTextColor

class CartAdapter(private var items: ArrayList<CartItem>,
                  private val requiredContext: Context,
                  private val cartTotalListener: CartTotalListener
): RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
        // Initialize Firebase
        private var auth = FirebaseAuth.getInstance()
    class CartViewHolder(private val binding: FoodInCartItemBinding): RecyclerView.ViewHolder(binding.root) {
        val tvNameFood = binding.tvNameFood
//        val tvNameRest = binding.tvNameRest
        val tvPrice = binding.tvPrice
        val image = binding.imageFood
        val btnPlus = binding.btnPlus
        val btnMinus = binding.btnMinus
        val tvCount = binding.tvCount
        val imgErase = binding.imgErase
        val tvQuantity = binding.tvCount

    }
    init {
        //Initialize Firebase
        val database = FirebaseDatabase.getInstance()
        val userId = auth.currentUser?.uid?:""
        val cartItemNumber = items.size

        val itemQuantities = IntArray(cartItemNumber){1}
        cartItemsReference = database.reference.child("user").child(userId).child("CartItems")
    }
    companion object{
        private var itemQuantities: IntArray = intArrayOf()
        private lateinit var cartItemsReference: DatabaseReference
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(FoodInCartItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {


        val model: CartItem = items[position]
        holder.tvNameFood.text = model.foodName
        holder.tvPrice.setGradientTextColor(
            Color.parseColor("#E85353"),
            Color.parseColor("#BE1515")
        )
        holder.tvQuantity.text = model.foodQuantity.toString()
        holder.tvPrice.text = model.foodPrice
        //holder.tvNameRest.text = model.getNameRest()
        val uri = Uri.parse(model.foodImage)
        Glide.with(requiredContext).load(uri).into(holder.image)

        holder.btnPlus.setOnClickListener {
            var positionRetrieve = position
            getUniqueKeyAtPosition(positionRetrieve){
                updateQuantity(holder, model, 1, it!!, positionRetrieve)
            }
        }

        holder.btnMinus.setOnClickListener {
            var positionRetrieve = position
            getUniqueKeyAtPosition(positionRetrieve){
                updateQuantity(holder, model, -1, it!!, positionRetrieve)
            }
        }
        holder.imgErase.setOnClickListener {
            var positionRetrieve = position
            getUniqueKeyAtPosition(positionRetrieve){
                if (it != null){
                    removeItem(positionRetrieve,it)
                }
            }
        }

    }

    private fun updateQuantity(holder: CartViewHolder, model: CartItem, i: Int, uniqueKey: String, position: Int) {
        val count = holder.tvCount.text.toString().toInt() + i
        if (count>0){
            holder.tvCount.text = count.toString()
            model.foodQuantity = count
            cartItemsReference.child(uniqueKey).child("foodQuantity").setValue(count)
                .addOnSuccessListener {
                    updateTotalAmount()
                }.addOnFailureListener {
                    Toast.makeText(requiredContext, "Not change the quantity", Toast.LENGTH_SHORT).show()
                }
        } else{
            removeItem(position, uniqueKey)
        }
    }
    private fun updateTotalAmount(){
        val total = items.sumOf { item ->
            var lastChar = item.foodPrice?.last()

            val price = item.foodPrice!!
            val priceValue = if (lastChar =='$'){
                price.dropLast(1).toDouble()
            }
            else {
                price.toDouble()
            }
            val quantity = item.foodQuantity ?: 0
            priceValue * quantity
        }
        cartTotalListener.onTotalChanged(total)
    }

    private fun removeItem(position: Int, uniqueKey: String) {

            if (uniqueKey!= null){
                cartItemsReference.child(uniqueKey).removeValue().addOnSuccessListener {
                    items.removeAt(position)
                    itemQuantities = itemQuantities.filterIndexed { index, i -> index != position  }.toIntArray()
                    updateTotalAmount()
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position,items.size)
                }.addOnFailureListener {
                    Toast.makeText(requiredContext,"Failed to delete",Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun getUniqueKeyAtPosition(positionRetrieve: Int, onComplete: (String?) -> Unit) {
        cartItemsReference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var uniqueKey: String? = null
                snapshot.children.forEachIndexed { index, dataSnapshot ->
                    if (index == positionRetrieve) {
                        uniqueKey = dataSnapshot.key
                        return@forEachIndexed
                    }
                }
                onComplete(uniqueKey)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}