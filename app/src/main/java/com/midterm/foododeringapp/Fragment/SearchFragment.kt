package com.midterm.foododeringapp.Fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.midterm.foododeringapp.Adapter.MenuAdapter
import com.midterm.foododeringapp.Model.FoodModel
import com.midterm.foododeringapp.R
import com.midterm.foododeringapp.databinding.FragmentSearchBinding
import com.midterm.foododeringapp.setGradientTextColor


class SearchFragment : Fragment() {
    private var binding: FragmentSearchBinding?= null

    private lateinit var adapter:MenuAdapter
    private var originalMenuFood = ArrayList<FoodModel>()
    private lateinit var database: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)


        binding?.tvMenu?.setGradientTextColor(
            Color.parseColor("#E85353"),
            Color.parseColor("#BE1515")
        )

        // retrieve menu item from database
        retrieveMenuItem()

        //setup for search view
        setupSearchFood()
        return binding?.root
    }

    private fun retrieveMenuItem() {
        database = FirebaseDatabase.getInstance()
        // reference to the Menu node
        val foodReference = database.reference.child("menu")
        foodReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children){
                    val menuItem = foodSnapshot.getValue(FoodModel::class.java)
                    menuItem?.let {
                        originalMenuFood.add(it)
                    }
                }
                showAllMenu()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun showAllMenu() {
        val filterMenuItem = ArrayList(originalMenuFood)
        setAdapter(filterMenuItem)
    }

    private fun setAdapter(menuItems: ArrayList<FoodModel>) {
       adapter = MenuAdapter(menuItems, requireContext())
        binding?.rvMenuFood?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rvMenuFood?.adapter = adapter
    }


    private fun setupSearchFood(){
        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterMenuItems((query!!))
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterMenuItems(newText!!)
                return true
            }
        })
    }

    private fun filterMenuItems(query: String) {
        val filterMenuItem = ArrayList(originalMenuFood.filter {
            it.foodName?.contains(query, ignoreCase = true) == true
        })
        setAdapter(filterMenuItem)
    }

    companion object {

    }
}