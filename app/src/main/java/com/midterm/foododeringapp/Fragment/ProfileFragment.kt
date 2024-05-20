package com.midterm.foododeringapp.Fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.midterm.foododeringapp.Model.UserModel
import com.midterm.foododeringapp.R
import com.midterm.foododeringapp.databinding.FragmentProfileBinding
import com.midterm.foododeringapp.setGradientTextColor

class ProfileFragment : Fragment() {

    private var binding: FragmentProfileBinding? = null

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        binding?.tvUser?.setGradientTextColor(
            Color.parseColor("#E85353"),
            Color.parseColor("#BE1515")
        )
        setUserData()

        binding?.btnEdit?.setOnClickListener {
            binding?.let {
                it.apply {
                    val isAnyEnable = etName.isEnabled || etAddress.isEnabled || etPhone.isEnabled || etEmail.isEnabled
                    etName.isEnabled= !isAnyEnable
                    etPhone.isEnabled= !isAnyEnable
                    etEmail.isEnabled= !isAnyEnable
                    etAddress.isEnabled= !isAnyEnable
                }
            }
        }

        binding?.btnSaveInfor?.setOnClickListener {
            val name = binding?.etName?.text.toString()
            val email = binding?.etEmail?.text.toString()
            val phone = binding?.etPhone?.text.toString()
            val address = binding?.etAddress?.text.toString()
            val password = binding?.etPassword?.text.toString()

            updateUserData(name, email, phone, address, password)
        }

        return binding?.root
    }

    private fun updateUserData(name: String, email: String, phone: String, address: String, password: String) {
        val userId = auth.currentUser?.uid
        if (userId != null){
            val userReference = database.getReference("user").child(userId)
            val userData = hashMapOf(
                "name" to name,
                "address" to address,
                "email" to email,
                "phone" to phone,
                "password" to password
            )
            userReference.setValue(userData).addOnSuccessListener {
                Toast.makeText(requireContext(), "Profile update successfully", Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Profile update failed", Toast.LENGTH_SHORT).show()
                }
        }
    }


    private fun setUserData() {
        val userId = auth.currentUser?.uid
        if (userId != null){
            val userReference = database.getReference("user").child(userId)

            userReference.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        val userProfile = snapshot.getValue(UserModel::class.java)
                        if (userProfile != null){
                            binding?.etName?.setText(userProfile.name)
                            binding?.etAddress?.setText(userProfile.address)
                            binding?.etEmail?.setText(userProfile.email)
                            binding?.etPhone?.setText(userProfile.phone)
                            binding?.etPassword?.setText(userProfile.password)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        }
    }

    companion object {

    }
}