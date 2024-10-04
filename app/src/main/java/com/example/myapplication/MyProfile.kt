package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.data_class.Tourists
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MyProfile : AppCompatActivity() {

    private lateinit var bckMenu: ImageButton
    private lateinit var editProfileBtn: Button
    private lateinit var deltProfileBtn: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference

    lateinit var gname: TextView
    lateinit var gphone: TextView
    lateinit var gemail: TextView
    lateinit var glocation: TextView

    private lateinit var touristlist: ArrayList<Tourists>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_profile)

        editProfileBtn = findViewById(R.id.edt_prof)
        bckMenu = findViewById(R.id.menu_To_bck)
        deltProfileBtn = findViewById(R.id.dlt_prof)

        gname = findViewById(R.id.edtusersname)
        gphone = findViewById(R.id.edtphone_no)
        gemail = findViewById(R.id.edtEmail)
        glocation = findViewById(R.id.edtlocation)

        // Initialize Firebase Auth and Database Reference
        auth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().getReference()

        touristlist = ArrayList()

        // Get current user UID
        val uid = auth.currentUser?.uid.toString()
        listenForProfileChanges(uid)

        editProfileBtn.setOnClickListener {
            navigateToEditProfile()
        }

        deltProfileBtn.setOnClickListener {
            navigatetoDeleteProfile()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        bckMenu.setOnClickListener {
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

    private fun listenForProfileChanges(uid: String) {
        databaseRef.child("Tourists").child(uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = snapshot.getValue(Tourists::class.java)
                    val phone = snapshot.getValue(Tourists::class.java)
                    val email = snapshot.getValue(Tourists::class.java)
                    val location = snapshot.getValue(Tourists::class.java)

                    val user = snapshot.getValue(Tourists::class.java)

                    println(name?.name)
                    println(email?.email)

                    if (user != null) {
                        gname.text = name?.name
                        gemail.text = email?.email
                        gphone.text = phone?.phone
                        glocation.text = location?.location
                    } else {
                        Toast.makeText(this@MyProfile, "User data not found", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    //TODO("Not yet implemented")
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


    private fun navigateToEditProfile() {
        val intent = Intent(this, EditMyProfile::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigatetoDeleteProfile() {
        val intent = Intent(this, DeleteProfile::class.java)
        startActivity(intent)
        finish()
    }
}