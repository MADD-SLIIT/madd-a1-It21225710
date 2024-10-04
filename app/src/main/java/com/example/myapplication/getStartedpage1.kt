package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class getStartedpage1 : AppCompatActivity() {

    lateinit var getstartedpage1 : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_get_startedpage1)

        getstartedpage1 = findViewById(R.id.getstartedPg1nxtBtn)

        getstartedpage1.setOnClickListener {
            val intent = Intent(this,getStartedpage2::class.java)
            startActivity(intent)
        }

    }
}