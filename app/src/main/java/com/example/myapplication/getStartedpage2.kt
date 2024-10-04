package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class getStartedpage2 : AppCompatActivity() {

    lateinit var getstartedpage2 : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_get_startedpage2)

        getstartedpage2 = findViewById(R.id.getstartedPg2nxtBtn)

        getstartedpage2.setOnClickListener {
            val intent = Intent(this,SelectedPage::class.java)
            startActivity(intent)
        }

    }
}