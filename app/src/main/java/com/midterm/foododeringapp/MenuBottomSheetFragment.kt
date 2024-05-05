package com.midterm.foododeringapp

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.midterm.foododeringapp.Adapter.MenuAdapter
import com.midterm.foododeringapp.Model.FoodModel
import com.midterm.foododeringapp.databinding.FragmentMenuBottomSheetBinding


class MenuBottomSheetFragment : BottomSheetDialogFragment() {
    private var binding: FragmentMenuBottomSheetBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBottomSheetBinding.inflate(inflater,container, false)
        var items: ArrayList<FoodModel> = ArrayList()
        items.add(FoodModel("Chocolate Pancakes","$7",R.drawable.food1,"Tiem Nha Na"))
        items.add(FoodModel("Fruit Salad","$5",R.drawable.food2,"An Yen"))
        items.add(FoodModel("Beef Noodle Soup","$15",R.drawable.food3,"Bun O Hong"))
        items.add(FoodModel("Chocolate Pancakes","$7",R.drawable.food1,"Tiem Nha Na"))
        items.add(FoodModel("Fruit Salad","$5",R.drawable.food2,"An Yen"))
        items.add(FoodModel("Beef Noodle Soup","$15",R.drawable.food3,"Bun O Hong"))
        items.add(FoodModel("Chocolate Pancakes","$7",R.drawable.food1,"Tiem Nha Na"))
        items.add(FoodModel("Fruit Salad","$5",R.drawable.food2,"An Yen"))
        items.add(FoodModel("Beef Noodle Soup","$15",R.drawable.food3,"Bun O Hong"))
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

        return binding?.root
    }

    companion object {

    }
}