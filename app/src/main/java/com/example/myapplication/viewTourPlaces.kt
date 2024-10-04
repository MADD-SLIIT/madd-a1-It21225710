package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapters.TourPlaceAdapter
import com.example.myapplication.data_class.TourPlace
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class viewTourPlaces : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tourPlaceAdapter: TourPlaceAdapter
    private val firestore = FirebaseFirestore.getInstance()

    private lateinit var bcktogTourplaceHome2 : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_tour_places)

        recyclerView = findViewById(R.id.tourPlacesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        bcktogTourplaceHome2 = findViewById(R.id.bckToTourPlaceHome2)

        bcktogTourplaceHome2.setOnClickListener {
            val intent = Intent(this,GuideHome::class.java)
            startActivity(intent)
            finish()
        }

        fetchTourPlaces()
    }

    private fun fetchTourPlaces() {
        firestore.collection("tourPlaces")
            .get()
            .addOnSuccessListener { result ->
                val tourPlaces = result.documents.mapNotNull { it.toObject<TourPlace>() }
                tourPlaceAdapter = TourPlaceAdapter(tourPlaces)
                recyclerView.adapter = tourPlaceAdapter
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    this,
                    "Failed to load tour places: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}