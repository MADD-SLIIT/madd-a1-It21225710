package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class CityClick1 : AppCompatActivity() {

    lateinit var bckToSearch: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_city_click)

        bckToSearch = findViewById(R.id.bck_to_searchc1)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        bckToSearch.setOnClickListener {
            onBackPressed()
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val fragmentManager = supportFragmentManager
                if (fragmentManager.backStackEntryCount > 0) {
                    fragmentManager.popBackStack() // Pop the top fragment from the back stack
                } else {
                    finish()  // No fragments left, finish the activity
                }
            }
        })

    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack() // Pop the top fragment from the back stack
        } else {
            super.onBackPressed() // Default back behavior
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}