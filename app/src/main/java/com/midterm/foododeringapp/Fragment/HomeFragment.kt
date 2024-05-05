package com.midterm.foododeringapp.Fragment

import android.graphics.Color
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.midterm.foododeringapp.Adapter.PopularAdapter
import com.midterm.foododeringapp.MenuBottomSheetFragment
import com.midterm.foododeringapp.Model.FoodModel
import com.midterm.foododeringapp.NotificationBottomFragment
import com.midterm.foododeringapp.R
import com.midterm.foododeringapp.databinding.FragmentHomeBinding
import com.midterm.foododeringapp.setGradientTextColor


class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
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
        return binding?.root


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


        var items: ArrayList<FoodModel> = ArrayList()
        items.add(FoodModel("Chocolate Pancakes","$7",R.drawable.food1,"Tiem Nha Na"))
        items.add(FoodModel("Fruit Salad","$5",R.drawable.food2,"An Yen"))
        items.add(FoodModel("Beef Noodle Soup","$15",R.drawable.food3,"Bun O Hong"))
        binding?.rcvPopularFoods?.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        var popularAdapter = PopularAdapter(items, requireContext())
        binding?.rcvPopularFoods?.adapter = popularAdapter



    }

    companion object {

    }
}