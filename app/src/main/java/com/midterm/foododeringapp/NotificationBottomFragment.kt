package com.midterm.foododeringapp

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.midterm.foododeringapp.Adapter.NotificationAdapter
import com.midterm.foododeringapp.databinding.FragmentNotificationBottomBinding



class NotificationBottomFragment : BottomSheetDialogFragment() {
    private var binding: FragmentNotificationBottomBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBottomBinding.inflate(layoutInflater,container,false)

        val notifications = listOf("Your order has been Canceled Successfully","Order has been taken by the driver","Congrats Your Order Placed")
        val notificationImages = listOf(R.drawable.congrats,R.drawable.trucksvg,R.drawable.sademoji)

        val adapter = NotificationAdapter(
            ArrayList(notifications),
            ArrayList(notificationImages)
        )
        binding?.tvUser?.setGradientTextColor(
            Color.parseColor("#E85353"),
            Color.parseColor("#BE1515")
        )
        binding?.rvNotifi?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding?.rvNotifi?.adapter = adapter
        return binding?.root
    }

    companion object {

    }
}