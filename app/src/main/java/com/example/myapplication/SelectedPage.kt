package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class SelectedPage : AppCompatActivity() {

    private lateinit var nav_SignUpTourist :Button
    private lateinit var nav_SignUpGuide : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_selected_page)

        nav_SignUpTourist = findViewById(R.id.selectPgLogToTouristbtn)
        nav_SignUpGuide = findViewById(R.id.selectPgLogToTouristGuidebtn)

        nav_SignUpTourist.setOnClickListener {
            val intent = Intent(this,SignUp::class.java)
            startActivity(intent)
        }

        nav_SignUpGuide.setOnClickListener {
            val intent = Intent(this,SignUpGuide::class.java)
            startActivity(intent)
        }

    }
}