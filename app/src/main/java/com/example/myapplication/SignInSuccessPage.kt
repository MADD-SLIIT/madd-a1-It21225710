package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivitySignInSuccessPageBinding
import com.example.myapplication.databinding.ActivityTouristSignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class SignInSuccessPage : AppCompatActivity() {

    private lateinit var binding: ActivitySignInSuccessPageBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

         //binding activity to root
        binding = ActivitySignInSuccessPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.SuccessBtn.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

    }
}