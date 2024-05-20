package com.midterm.foododeringapp

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import com.midterm.foododeringapp.databinding.ActivityLoginBinding

class LoginActivity<FirebaseUser> : AppCompatActivity() {

    private var binding: ActivityLoginBinding? = null


    private lateinit var email:String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var googleSignInClient : GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.tvNameApp?.setGradientTextColor(
            Color.parseColor("#FEAD1D"),
            Color.parseColor("#FF9012")
        )
        binding?.tvTitle?.setGradientTextColor(
            Color.parseColor("#E85353"),
            Color.parseColor("#BE1515")
        )

        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions)


        binding?.tvDontHaveAccount?.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
            finish()
        }

        binding?.btnLogin?.setOnClickListener {
            //get data from text field
            email = binding?.etName?.text.toString().trim()
            password = binding?.etPassword?.text.toString().trim()
            if(email.isEmpty()||password.isEmpty()){
                Toast.makeText(this,"Please fill details",Toast.LENGTH_LONG).show()
            } else{
                createUser()
            }

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

    private fun createUser() {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful){
                val user = auth.currentUser
                updateUI(user!!)
            }else{
                Toast.makeText(this,"Login failed", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updateUI(user: com.google.firebase.auth.FirebaseUser) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("dataUser", user?.uid)
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser!= null){
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("dataUser", currentUser.uid)
            startActivity(intent)
            finish()
        }
    }

}