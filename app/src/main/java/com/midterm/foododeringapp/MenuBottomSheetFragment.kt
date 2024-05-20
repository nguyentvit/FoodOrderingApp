package com.midterm.foododeringapp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.midterm.foododeringapp.Adapter.MenuAdapter
import com.midterm.foododeringapp.Model.FoodModel
import com.midterm.foododeringapp.databinding.FragmentMenuBottomSheetBinding


class MenuBottomSheetFragment : BottomSheetDialogFragment() {
    private var binding: FragmentMenuBottomSheetBinding? = null
    private lateinit var database: FirebaseDatabase
    private var menuItem: ArrayList<FoodModel> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBottomSheetBinding.inflate(inflater,container, false)
        var items: ArrayList<FoodModel> = ArrayList()

        binding?.rvMenuFood?.layoutManager =
            LinearLayoutManager(requireContext())
        var menuAdapter = MenuAdapter(items,requireContext())
        binding?.rvMenuFood?.adapter = menuAdapter
        binding?.tvCart?.setGradientTextColor(
            Color.parseColor("#E85353"),
            Color.parseColor("#BE1515")
        )

        binding?.btnBack?.setOnClickListener {
            dismiss()
        }
        retrieveMenuItems()
        return binding?.root
    }
    private fun retrieveMenuItems() {
        database = FirebaseDatabase.getInstance()
        val foodRef: DatabaseReference = database.reference.child("menu")
        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                menuItem.clear()
                for(dataSnapshot in snapshot.children){
                    val item = dataSnapshot.getValue(FoodModel::class.java)
                    if (item != null) {
                        menuItem.add(item)
                        setAdapter()
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("databaseError","Error: ${error.message}")
            }
        })
    }

    private fun setAdapter() {
        val adapter = MenuAdapter(menuItem,requireContext())

        binding?.rvMenuFood?.layoutManager= LinearLayoutManager(requireContext())
        binding?.rvMenuFood?.adapter= adapter
    }

    companion object {

    }
}