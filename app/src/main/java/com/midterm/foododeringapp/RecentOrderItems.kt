package com.midterm.foododeringapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.midterm.foododeringapp.Adapter.RecentBuyAdapter
import com.midterm.foododeringapp.Model.OrderDetails
import com.midterm.foododeringapp.databinding.ActivityRecentOrderItemsBinding

class RecentOrderItems : AppCompatActivity() {
    private var binding: ActivityRecentOrderItemsBinding? = null
    private var listFoodNames: ArrayList<String>? = null
    private var listFoodPrices: ArrayList<String>? = null
    private var listFoodQuantity: ArrayList<String>? = null
    private var listFoodImage: ArrayList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecentOrderItemsBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val recentOrderItems = intent.getParcelableExtra<OrderDetails>("RecentBuyOrderItem")

        recentOrderItems?.let {
            Log.d("hie", ""+ it.foodNames?.last())
            listFoodImage = it.foodImages as ArrayList<String>
            listFoodNames = it.foodNames as ArrayList<String>
            listFoodPrices = it.foodPrices as ArrayList<String>
            listFoodQuantity = it.foodQuantities as ArrayList<String>
        }


        setAdapter()
        binding?.btnBack?.setOnClickListener {
            finish()
        }
    }

    private fun setAdapter() {
        binding?.rvRecentItems?.layoutManager = LinearLayoutManager(this)
        val adapter = RecentBuyAdapter(this, listFoodNames!!, listFoodImage!!, listFoodPrices!!, listFoodQuantity!! )
        binding?.rvRecentItems?.adapter = adapter
    }
}