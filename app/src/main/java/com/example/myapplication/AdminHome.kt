package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AdminHome : AppCompatActivity() {

    lateinit var adminlgout : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_home)

        adminlgout = findViewById(R.id.adminLogout)

        adminlgout.setOnClickListener {
            val intent = Intent(this,SignIn::class.java)
            startActivity(intent)
        }

    }
}