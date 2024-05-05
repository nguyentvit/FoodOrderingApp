package com.midterm.foododeringapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.midterm.foododeringapp.databinding.ActivityLoginBinding
import com.midterm.foododeringapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        var navController = findNavController(R.id.fragment)
        binding?.bottomNavigationView?.setupWithNavController(navController)


    }
}