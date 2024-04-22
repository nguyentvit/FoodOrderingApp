package com.midterm.foododeringapp

import android.graphics.LinearGradient
import android.graphics.Shader
import android.widget.TextView

fun TextView.setGradientTextColor(startColor: Int, endColor: Int) {
    val paint = paint
    val width = paint.measureText(text.toString())
    paint.shader = LinearGradient(
        0f, 0f, width, textSize,
        intArrayOf(startColor, endColor),
        null, Shader.TileMode.REPEAT
    )
}
