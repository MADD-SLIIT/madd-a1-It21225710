package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivitySignInGuideBinding
import com.example.myapplication.databinding.ActivityTouristSignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignInGuide : AppCompatActivity() {

    private lateinit var binding: ActivitySignInGuideBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //binding activity to root
        binding = ActivitySignInGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance()

        //firebase Database Reference
        databaseRef = FirebaseDatabase.getInstance().reference

        binding.GuideSignUpRedirectBtn.setOnClickListener {
            val intent = Intent(this, SignUpGuide::class.java)
            startActivity(intent)
        }

        binding.GuideSignInBtn.setOnClickListener {
            val email = binding.edtGuideSigninEmail.text.toString()
            val password = binding.edtGuideSigninPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginGuide(email, password)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun loginGuide(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val uid = firebaseAuth.currentUser?.uid
                    if (uid != null) {
                        fetchGuidedata(uid)
                    }
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    loginGuideHome()
                } else {
                    Toast.makeText(
                        this,
                        "Login Failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
    }


    private fun fetchGuidedata(uid: String) {
        databaseRef.child("Guides").child(uid).get().addOnSuccessListener {
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

    private fun loginGuideHome() {
        val intent = Intent(this, GuideHome::class.java)
        startActivity(intent)
        finish()
    }
}