package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
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

class EditMyProfile : AppCompatActivity() {

    lateinit var viewProfileback: ImageButton
    lateinit var edtprofilebtn: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference

    lateinit var gname: EditText
    lateinit var gphone: EditText
    lateinit var gemail: EditText
    lateinit var getpw: EditText
    lateinit var getlocation: EditText

    private lateinit var Touristlist: ArrayList<Tourists>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_my_profile)

        viewProfileback = findViewById(R.id.view_To_bck)
        edtprofilebtn = findViewById(R.id.edt_profile_accept)

        gname = findViewById(R.id.edtusersname)
        gphone = findViewById(R.id.edtphone_no)
        gemail = findViewById(R.id.edtEmail)
        getpw = findViewById(R.id.edtpw)
        getlocation = findViewById(R.id.edtlocation)

        // Initialize Firebase Auth and Database Reference
        auth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().getReference()

        Touristlist = ArrayList()

        viewProfileback.setOnClickListener {
            viewProfileback()
        }

        edtprofilebtn.setOnClickListener {
            updateprofile()
        }

        retriveTouristprofile()

    }

    private fun retriveTouristprofile() {
        val currentTourist = auth.currentUser
        currentTourist?.let {
            val uid = it.uid

            databaseRef.child("Tourists").child(uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        val names = snapshot.child("name").value.toString()
                        val phoneNO = snapshot.child("phone").value.toString()
                        val emails = snapshot.child("email").value.toString()
                        val pw = snapshot.child("password").value.toString()
                        val location = snapshot.child("location").value.toString()

                        findViewById<EditText>(R.id.edtusersname).setText(names)
                        findViewById<EditText>(R.id.edtEmail).setText(emails)
                        findViewById<EditText>(R.id.edtphone_no).setText(phoneNO)
                        findViewById<EditText>(R.id.edtpw).setText(pw)
                        findViewById<EditText>(R.id.edtlocation).setText(location)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        //
                    }
                })

        }
    }

    private fun updateprofile() {

        val newName = gname.text.toString()
        val newphone = gphone.text.toString()
        val newemail = gemail.text.toString()
        val newpw = getpw.text.toString()
        val newlocation = getlocation.text.toString()

        databaseRef.child("Tourists").child(auth.currentUser?.uid.toString())
            .setValue(
                Tourists(
                    newName,
                    newemail,
                    auth.currentUser?.uid.toString(),
                    newpw,
                    newlocation,
                    newphone
                )
            )
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@EditMyProfile,
                        "Profile updated successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this, MyProfile::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this@EditMyProfile,
                        "Failed to update profile",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun viewProfileback() {
        val intent = Intent(this, MyProfile::class.java)
        startActivity(intent)
        finish()
    }
}