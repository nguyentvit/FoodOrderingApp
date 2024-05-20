package com.midterm.foododeringapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.midterm.foododeringapp.Model.CartItem
import com.midterm.foododeringapp.Model.OrderDetails
import com.midterm.foododeringapp.databinding.ActivityPayOutBinding
import java.net.Inet4Address

class PayOutActivity : AppCompatActivity() {

    private var binding:ActivityPayOutBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var name: String
    private lateinit var address: String
    private lateinit var phone: String
    private var foodNames: MutableList<String>? = mutableListOf()
    private var foodImages: MutableList<String>? = mutableListOf()
    private var foodPrices: MutableList<String>? = mutableListOf()
    private var foodQuantities: MutableList<String>? = mutableListOf()
    private lateinit var totalAmount: String
    private var cartItems: ArrayList<CartItem> = ArrayList()
    private lateinit var databaseReference: DatabaseReference
    private lateinit var userId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayOutBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //Initialize Firebase and User details
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        setUserData()

        val receivedCartItems: ArrayList<CartItem>? = intent.getParcelableArrayListExtra("cartItems")


        receivedCartItems?.forEach { cartItem ->
            foodNames?.add(cartItem.foodName ?: "")
            foodImages?.add(cartItem.foodImage ?: "")
            foodPrices?.add(cartItem.foodPrice ?: "")
            foodQuantities?.add(cartItem.foodQuantity.toString())
        }


        binding?.btnPlaceOrder?.setOnClickListener {
            //get data from textview
            name = binding?.etName?.text.toString().trim()
            address = binding?.etAddress?.text.toString().trim()
            phone = binding?.etPhone?.text.toString().trim()
            if (name.isBlank()&& address.isBlank()&& phone.isBlank()){
                Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show()
            }else{
                placeOrder()
            }


        }
        binding?.btnBack?.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }

    private fun placeOrder() {
        userId = auth.currentUser?.uid?:""
        val time = System.currentTimeMillis()
        val itemPushKey = databaseReference.child("OrderDetails").push().key
        val orderDetails = OrderDetails(userId,name, foodNames,foodImages, foodPrices, foodQuantities,  address,
            totalAmount, phone, time, itemPushKey,false, false)
        val orderReference = databaseReference.child("OrderDetails").child(itemPushKey!!)
        orderReference.setValue(orderDetails).addOnSuccessListener {
            val bottomSheetDialog = CongratsBottomSheet()
            bottomSheetDialog.show(supportFragmentManager,"test")
            removeItemsFromCart()
            addOrderHistory(orderDetails)
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to order", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addOrderHistory(orderDetails: OrderDetails){
        databaseReference.child("user").child(userId).child("BuyHistory")
            .child(orderDetails.itemPushKey!!)
            .setValue(orderDetails).addOnSuccessListener {

            }
    }

    private fun removeItemsFromCart() {
        val cartItemsReference = databaseReference.child("user").child(userId).child("CartItems")
        cartItemsReference.removeValue()
    }

    private fun setUserData() {
        val user = auth.currentUser
        if (user!=null){
            userId = user.uid
            val userReference = databaseReference.child("user").child(userId)

            userReference.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        val name = snapshot.child("name").getValue(String::class.java)?:""
                        val address = snapshot.child("address").getValue(String::class.java)?:""
                        val phone = snapshot.child("phone").getValue(String::class.java)?:""
                        binding?.apply {
                            etName.setText(name)
                            etAddress.setText(address)
                            etPhone.setText(phone)
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        }
        totalAmount = intent.getStringExtra("totalAmount").toString()
        binding?.etTotal?.setText(totalAmount)
    }
}