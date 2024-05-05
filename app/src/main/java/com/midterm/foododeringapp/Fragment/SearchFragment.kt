package com.midterm.foododeringapp.Fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.midterm.foododeringapp.Adapter.MenuAdapter
import com.midterm.foododeringapp.Model.FoodModel
import com.midterm.foododeringapp.R
import com.midterm.foododeringapp.databinding.FragmentSearchBinding
import com.midterm.foododeringapp.setGradientTextColor


class SearchFragment : Fragment() {
    private var binding: FragmentSearchBinding?= null

    private lateinit var adapter:MenuAdapter
    private var originalMenuFood = ArrayList<FoodModel>()
    private var filterMenuItem = ArrayList<FoodModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        originalMenuFood.add(FoodModel("Chocolate Pancakes","$7",R.drawable.food1,"Tiem Nha Na"))
        originalMenuFood.add(FoodModel("Fruit Salad","$5",R.drawable.food2,"An Yen"))
        originalMenuFood.add(FoodModel("Beef Noodle Soup","$15",R.drawable.food3,"Bun O Hong"))
        originalMenuFood.add(FoodModel("Chocolate Pancakes","$7",R.drawable.food1,"Tiem Nha Na"))
        originalMenuFood.add(FoodModel("Fruit Salad","$5",R.drawable.food2,"An Yen"))
        originalMenuFood.add(FoodModel("Beef Noodle Soup","$15",R.drawable.food3,"Bun O Hong"))
        adapter = MenuAdapter(filterMenuItem,requireContext())
        binding?.rvMenuFood?.layoutManager =
            LinearLayoutManager(requireContext())
        binding?.rvMenuFood?.adapter = adapter
        binding?.tvMenu?.setGradientTextColor(
            Color.parseColor("#E85353"),
            Color.parseColor("#BE1515")
        )
        //setup for search view
        setupSearchFood()
        //show all menu
        showAllMenu()
        return binding?.root
    }

    private fun showAllMenu() {
        filterMenuItem.clear()
        filterMenuItem.addAll(originalMenuFood)
        adapter.notifyDataSetChanged()
    }

    private fun setupSearchFood(){
        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterMenuItems((query))
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterMenuItems(newText!!)
                return true
            }
        })
    }

    private fun filterMenuItems(query: String?) {
        filterMenuItem.clear()
        originalMenuFood.forEachIndexed { index, foodModel ->
            if(foodModel.getNameFood().contains(query!!, ignoreCase = true)){
                filterMenuItem.add(foodModel)
            }
            adapter.notifyDataSetChanged()
        }
    }

    companion object {

    }
}