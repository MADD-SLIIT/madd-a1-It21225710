package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.myapplication.fragments.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class Home : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        /*supportFragmentManager.beginTransaction()
            .add(R.id.home_frgment, HomeFragment())
            .addToBackStack(null)
            .commit()*/

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_containter) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        setupWithNavController(bottomNavigationView, navController)

    }
}