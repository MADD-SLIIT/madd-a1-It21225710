package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.data_class.Tourists
import com.example.myapplication.databinding.ActivityTouristSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivityTouristSignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //binding activity to root
        binding = ActivityTouristSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance()

        //firebase Database Reference
        databaseRef = FirebaseDatabase.getInstance().reference

        binding.bckToSelectedScreen.setOnClickListener {
            val intent = Intent(this, SelectedPage::class.java)
            startActivity(intent)
        }

        binding.TouristSignUpBtn.setOnClickListener {
            val name = binding.edtTuristName.text.toString()
            val email = binding.edtTuristEmail.text.toString()
            val phone = binding.edtTuristNo.text.toString()
            val location = binding.edtTouristLocation.toString()
            val pw = binding.edtTuristpassword.text.toString()
            val cpw = binding.edtTuristconfirmpassword.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && location.isNotEmpty() && pw.isNotEmpty() && cpw.isNotEmpty()) {
                SignUpTourist(name, email, phone, location, pw, cpw)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.TouristSignInRedirectBtn.setOnClickListener {
            TouristSignInDirect()
        }

    }

    private fun SignUpTourist(
        name: String,
        email: String,
        phone: String,
        location: String,
        pw: String,
        cpw: String
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, pw)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val uid = firebaseAuth.currentUser?.uid
                    if (uid != null) {
                        addUserToDatabase(name, email, uid, pw, location, phone)
                    }
                    Toast.makeText(this, "SignUp Success", Toast.LENGTH_SHORT).show()
                    signupsuccess()
                } else {
                    Toast.makeText(
                        this,
                        "Signup Failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
    }


    private fun addUserToDatabase(
        name: String,
        email: String,
        uid: String,
        pw: String,
        location: String,
        phone: String
    ) {
        val tourist = Tourists(name, email, uid, pw, location, phone)
        databaseRef.child("Tourists").child(uid).setValue(tourist)
    }


    private fun signupsuccess() {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
        finish()
    }

    fun TouristSignInDirect() {
        val intent = Intent(this, SignIn::class.java)
        startActivity(intent)
        finish()
    }

}
