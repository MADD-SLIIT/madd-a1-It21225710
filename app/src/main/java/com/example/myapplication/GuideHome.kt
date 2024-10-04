package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GuideHome : AppCompatActivity() {

    lateinit var logouttourplace : Button
    lateinit var addTourplaces : Button
    lateinit var viewTourplaces : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_guide_home)

        logouttourplace = findViewById(R.id.tourplaceLogout)
        addTourplaces = findViewById(R.id.tourplaceAdd)
        viewTourplaces =findViewById(R.id.tourplaceview)

        addTourplaces.setOnClickListener {
            val intent = Intent(this,AddTourPlaces::class.java)
            startActivity(intent)
        }

        viewTourplaces.setOnClickListener {
            val intent = Intent(this,viewTourPlaces::class.java)
            startActivity(intent)
        }

        logouttourplace.setOnClickListener {
            val intent = Intent(this,SignInGuide::class.java)
            startActivity(intent)
        }

    }
}