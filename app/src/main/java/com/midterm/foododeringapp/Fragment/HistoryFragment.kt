package com.midterm.foododeringapp.Fragment

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.midterm.foododeringapp.Adapter.BuyAgainAdapter
import com.midterm.foododeringapp.Adapter.CartAdapter
import com.midterm.foododeringapp.Model.FoodModel
import com.midterm.foododeringapp.Model.OrderDetails
import com.midterm.foododeringapp.R
import com.midterm.foododeringapp.databinding.FragmentHistoryBinding
import com.midterm.foododeringapp.setGradientTextColor


class HistoryFragment : Fragment() {
    private var binding: FragmentHistoryBinding? = null
    private lateinit var adapter: BuyAgainAdapter
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var userId: String
    private var listOfOrderItem: MutableList<OrderDetails> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater,container,false)
        //Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        //Retrieve and display The User Order History
        binding?.tvUser?.setGradientTextColor(
            Color.parseColor("#E85353"),
            Color.parseColor("#BE1515")
        )
        retrieveBuyHistory()
// recent buy button click
//        binding?.recentBuyItem?.setOnClickListener {
//            seeItemsRecentBuy()
//        }


        return binding?.root
    }

//    private fun seeItemsRecentBuy() {
//        listOfOrderItem.firstOrNull()?.let {
//            val intent = Intent(requireContext())
//        }
//    }

    private fun retrieveBuyHistory() {
        binding?.recentBuyItem?.visibility = View.INVISIBLE
        userId = auth.currentUser?.uid?:""

        val buyItemReference = database.reference.child("user").child(userId).child("BuyHistory")
        val shortingQuery = buyItemReference.orderByChild("currentTime")
        shortingQuery.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (buySnapshot in snapshot.children){
                    val buyHistoryItem = buySnapshot.getValue(OrderDetails::class.java)
                    buyHistoryItem?.let {
                        listOfOrderItem.add(it)
                    }
                }
                listOfOrderItem.reverse()
                if(listOfOrderItem.isNotEmpty()){
                    setDataInRecentBuyItem()
                    setPreviousBuyItemRecyclerView()
                }else{
                    Log.d("hi","hi")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun setDataInRecentBuyItem() {
        binding?.recentBuyItem?.visibility = View.VISIBLE
        val recentOrderItem = listOfOrderItem.first()
        recentOrderItem?.let {
            Log.d("hi","hi1")
            with(binding){
                this?.tvNameFood?.text = it.foodNames?.firstOrNull()?:""
                this?.tvPrice?.text= it.foodPrices?.firstOrNull()?:""
                val image = it.foodImages?.firstOrNull()?:""
                val uri = Uri.parse(image)
                Glide.with(requireContext()).load(uri).into(this?.imageFood!!)

                listOfOrderItem.reverse()

            }
        }
    }

    private fun setPreviousBuyItemRecyclerView() {
        val buyAgainFood = ArrayList<FoodModel>()
        for (i in 1 until listOfOrderItem.size){
            val foodName = listOfOrderItem[i].foodNames?.firstOrNull()
            val foodPrice = listOfOrderItem[i].foodPrices?.firstOrNull()
            val foodImage = listOfOrderItem[i].foodImages?.firstOrNull()
            Log.d("hi",foodImage.toString())
            buyAgainFood.add(FoodModel(foodName,foodPrice,foodImage))
        }
        binding?.rvHistoryFoodBought?.layoutManager = LinearLayoutManager(requireContext())
        adapter = BuyAgainAdapter(buyAgainFood, requireContext())
        binding?.rvHistoryFoodBought?.adapter = adapter
    }



}