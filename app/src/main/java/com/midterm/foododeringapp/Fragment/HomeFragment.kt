package com.midterm.foododeringapp.Fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.midterm.foododeringapp.Adapter.PopularAdapter
import com.midterm.foododeringapp.MenuBottomSheetFragment
import com.midterm.foododeringapp.Model.FoodModel
import com.midterm.foododeringapp.NotificationBottomFragment
import com.midterm.foododeringapp.R
import com.midterm.foododeringapp.databinding.FragmentHomeBinding
import com.midterm.foododeringapp.setGradientTextColor


class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private lateinit var database: FirebaseDatabase
    private var menuItem: ArrayList<FoodModel> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        binding?.tvViewMore?.setOnClickListener {
            val bottomSheetDialog = MenuBottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager, "text")
        }
        binding?.imgNotification?.setOnClickListener {
            val bottomSheetDialog = NotificationBottomFragment()
            bottomSheetDialog.show(parentFragmentManager,"text")
        }

        retrieveMenuItems()

        return binding?.root


    }

    private fun retrieveMenuItems() {
        database = FirebaseDatabase.getInstance()
        val foodRef: DatabaseReference = database.reference.child("menu")
        foodRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(dataSnapshot in snapshot.children){
                    val item = dataSnapshot.getValue(FoodModel::class.java)
                    menuItem.add(item!!)
                    Log.d("food",item?.foodName!!)
                }
                randomPopularItem()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("databaseError","Error: ${error.message}")
            }
        })
    }
    private fun randomPopularItem(){
        val index = menuItem.indices.toList().shuffled()
        val menuItemToShow = 6
        val subnetMenuItems = ArrayList<FoodModel>()

        index.take(menuItemToShow).forEach {
            subnetMenuItems.add(menuItem[it])
        }
        binding?.rcvPopularFoods?.layoutManager =
            LinearLayoutManager(view?.context, LinearLayoutManager.VERTICAL, false)
        var popularAdapter = PopularAdapter(subnetMenuItems, requireContext())
        binding?.rcvPopularFoods?.adapter = popularAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))

        val imageSlider = binding?.imageSlider
        imageSlider?.setImageList(imageList)
        imageSlider?.setImageList(imageList, ScaleTypes.FIT)

        binding?.tvTitle?.setGradientTextColor(
            Color.parseColor("#BE1515"),
            Color.parseColor("#E85353")
        )







    }

    companion object {

    }
}