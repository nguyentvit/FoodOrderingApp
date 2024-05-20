package com.midterm.foododeringapp

import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.midterm.foododeringapp.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private var binding: ActivitySplashBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        Handler().postDelayed({
            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
            finish()
        },3000)

        binding?.tvNameApp?.setGradientTextColor(
            Color.parseColor("#FEAD1D"),
            Color.parseColor("#FF9012")
        )


    }
    
}