package com.midterm.foododeringapp.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.midterm.foododeringapp.Adapter.CartAdapter
import com.midterm.foododeringapp.CartTotalListener
import com.midterm.foododeringapp.Model.CartItem
import com.midterm.foododeringapp.NotificationBottomFragment
import com.midterm.foododeringapp.PayOutActivity
import com.midterm.foododeringapp.R
import com.midterm.foododeringapp.databinding.FragmentCartBinding


class CartFragment : Fragment(), CartTotalListener {

    private var binding: FragmentCartBinding? = null
    private var cartItems : ArrayList<CartItem> = ArrayList()
    private lateinit var total: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater,container,false)


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding?.imgNotification?.setOnClickListener {
            val bottomSheetDialog = NotificationBottomFragment()
            bottomSheetDialog.show(parentFragmentManager,"text")
        }
        binding?.btnDischarge?.setOnClickListener {
            if (cartItems.isNullOrEmpty()){
                Toast.makeText(requireContext(),"Cart is empty, please add Food", Toast.LENGTH_SHORT).show()
            }else{
                total = binding?.tvTotalValue?.text.toString()
                var intent = Intent(requireContext(),PayOutActivity::class.java)
                intent.putExtra("totalAmount",total)
                intent.putParcelableArrayListExtra("cartItems",cartItems)
                startActivity(intent)
            }
        }

        retrieveCartItem()

        return binding?.root
    }

    private fun retrieveCartItem() {
        val userId = auth.currentUser?.uid?:""
        val cartItemRef = database.reference.child("user").child(userId).child("CartItems")
        cartItemRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children){
                    val cartItem = foodSnapshot.getValue(CartItem::class.java)
                    cartItems.add(cartItem!!)
                }
                setAdapter()
                updateTotalAmount()
            }



            override fun onCancelled(error: DatabaseError) {
                Log.d("Error",""+error.message)
            }

        })

    }
    private fun setAdapter() {
        binding?.rvFoodsInCart?.layoutManager =
            LinearLayoutManager(view?.context, LinearLayoutManager.VERTICAL, false)
        val adapter = CartAdapter(cartItems, requireContext(), this@CartFragment)
        binding?.rvFoodsInCart?.adapter = adapter
    }
    override fun onTotalChanged(total: Double) {
        binding?.tvTotalValue?.text = getString(R.string.total_format, total)
    }
    private fun updateTotalAmount() {
        val total = cartItems.sumOf { item ->
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
        binding?.tvTotalValue?.text = getString(R.string.total_format, total)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    companion object {

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


}