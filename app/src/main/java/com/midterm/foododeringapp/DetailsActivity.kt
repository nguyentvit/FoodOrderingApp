package com.midterm.foododeringapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.midterm.foododeringapp.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private var binding: ActivityDetailsBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val foodName = intent.getStringExtra("ItemName")
        val foodImage = intent.getIntExtra("ItemImage",0)
        binding?.tvNameFood?.text = foodName
        binding?.imgFood?.setImageResource(foodImage)
        binding?.btnBack?.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}