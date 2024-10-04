package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GetStartedpage : AppCompatActivity() {

    lateinit var getstartedpagebtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_get_startedpage)

       getstartedpagebtn = findViewById(R.id.getstartedBtn)

        getstartedpagebtn.setOnClickListener {
            val intent = Intent(this, getStartedpage1::class.java)
            startActivity(intent)
        }

    }
}