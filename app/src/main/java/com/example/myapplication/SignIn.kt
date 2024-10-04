package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityTouristSignInBinding
import com.example.myapplication.databinding.ActivityTouristSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignIn : AppCompatActivity() {

    private lateinit var binding: ActivityTouristSignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference

    // Predefined admin email and password
    private val adminEmail = "admin@gmail.com"
    private val adminPassword = "Admin123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //binding activity to root
        binding = ActivityTouristSignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance()

        //firebase Database Reference
        databaseRef = FirebaseDatabase.getInstance().reference

        binding.TouristSignUpRedirectBtn.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        binding.TouristSignInBtn.setOnClickListener {
            val email = binding.edtTuristSigninEmail.text.toString()
            val password = binding.edtTuristSigninPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun loginUser(email: String, pw: String) {

        if (email == adminEmail && pw == adminPassword) {
            // Direct to Admin page
            Toast.makeText(this, "Admin Login Successful", Toast.LENGTH_SHORT).show()
            loginToAdminPage()
        } else {
            firebaseAuth.signInWithEmailAndPassword(email, pw)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val uid = firebaseAuth.currentUser?.uid
                        if (uid != null) {
                            fetchUserData(uid)
                        }
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        LoginToHome()
                    } else {
                        Toast.makeText(
                            this,
                            "Login Failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
        }

    }

    private fun loginToAdminPage() {
        val intent = Intent(this, AdminHome::class.java)
        startActivity(intent)
        finish()
    }

    private fun fetchUserData(uid: String) {
        databaseRef.child("Tourists").child(uid).get().addOnSuccessListener {
            if (it.exists()) {
                val email = it.child("email").value
                val name = it.child("name").value
                val password = it.child("password").value

                Toast.makeText(this, "Welcome,$name!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "User data not found", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to retrieve user data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun LoginToHome() {
        val intent = Intent(this, SignInSuccessPage::class.java)
        startActivity(intent)
        finish()
    }
}