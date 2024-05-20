package com.midterm.foododeringapp

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.midterm.foododeringapp.Model.UserModel
import com.midterm.foododeringapp.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private var binding: ActivitySignUpBinding? = null

    private lateinit var email:String
    private lateinit var password: String
    private lateinit var username: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var googleSignInClient : GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        //initialize Firebase database
        auth = Firebase.auth

        database = Firebase.database.reference

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions)

        binding?.btnCreateAccount?.setOnClickListener {
            username = binding?.etName?.text.toString()
            email = binding?.etEmail?.text.toString().trim()
            password = binding?.etPassword?.text.toString().trim()
            if(email.isEmpty()||username.isEmpty()||password.isEmpty()){
                Toast.makeText(this,"please fill all the details",Toast.LENGTH_LONG).show()
            } else{
                createAccount(email, password)
            }
        }

        binding?.tvNameApp?.setGradientTextColor(
            Color.parseColor("#FEAD1D"),
            Color.parseColor("#FF9012")
        )

        binding?.tvTitle?.setGradientTextColor(
            Color.parseColor("#E85353"),
            Color.parseColor("#BE1515")
        )
        binding?.tvDontHaveAccount?.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding?.btnGoogle?.setOnClickListener {
            val signIntent = googleSignInClient.signInIntent
            launcher.launch(signIntent)
        }

    }
    private var launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            if (task.isSuccessful){
                val account: GoogleSignInAccount? = task.result
                val credential = GoogleAuthProvider.getCredential(account?.idToken,null)
                auth.signInWithCredential(credential).addOnCompleteListener {
                    taskCre ->
                    if (taskCre.isSuccessful){
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this,"Sign in failed",Toast.LENGTH_LONG).show()
                    }
                }
            }else{
                Toast.makeText(this,"Sign in failed",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            task ->
            if (task.isSuccessful){
                Toast.makeText(this,"create account successfully",Toast.LENGTH_LONG).show()
                saveUserData()
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            } else{
                Toast.makeText(this, "Account creation failed",Toast.LENGTH_LONG).show()
                Log.d("Account","createAccount: Failure")
            }
        }
    }

    private fun saveUserData() {
        username = binding?.etName?.text.toString()
        password = binding?.etPassword?.text.toString().trim()
        email = binding?.etEmail?.text.toString().trim()
        var user = UserModel(username,email,password)
        var userId = FirebaseAuth.getInstance().currentUser!!.uid
        database.child("user").child(userId).setValue(user)
    }
}