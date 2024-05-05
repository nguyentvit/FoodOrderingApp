package com.midterm.foododeringapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.midterm.foododeringapp.databinding.FragmentCongratsBottomSheetBinding


class CongratsBottomSheet : BottomSheetDialogFragment() {

    private var binding: FragmentCongratsBottomSheetBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCongratsBottomSheetBinding.inflate(inflater,container,false)
        binding?.btnGoHome?.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
        return binding?.root
    }

    companion object {

    }
}