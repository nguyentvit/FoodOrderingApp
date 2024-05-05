package com.midterm.foododeringapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.midterm.foododeringapp.databinding.ActivityChooseLocationBinding

class ChooseLocationActivity : AppCompatActivity() {

    private var binding: ActivityChooseLocationBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseLocationBinding.inflate(layoutInflater)
        setContentView(binding?.root)

    }
}