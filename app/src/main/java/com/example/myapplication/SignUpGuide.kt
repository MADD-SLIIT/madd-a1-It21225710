package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.data_class.Guide
import com.example.myapplication.databinding.ActivitySignUpGuideBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class SignUpGuide : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpGuideBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var storageRef: FirebaseStorage

    // Image selection
    private lateinit var imageView: ImageView
    private val PICK_IMAGE_REQUEST = 1
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //binding activity to root
        binding = ActivitySignUpGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance()

        //firebase Database Reference
        databaseRef = FirebaseDatabase.getInstance().reference

        // Firebase Storage Reference
        storageRef = FirebaseStorage.getInstance()

        // Image selection
        imageView = findViewById(R.id.guideImageView)
        val chooseImageBtn = findViewById<Button>(R.id.chooseImageBtn)

        chooseImageBtn.setOnClickListener {
            openImageChooser()
        }

        binding.bckToSelectedScreenGuide.setOnClickListener {
            val intent = Intent(this, SelectedPage::class.java)
            startActivity(intent)
        }

        binding.GuideSignUpBtn.setOnClickListener {
            val guide_name = binding.edtGuideName.text.toString()
            val guide_email = binding.edtGuideEmail.text.toString()
            val guide_phone = binding.edtGuideNo.text.toString()
            val guide_pw = binding.edtGuidepassword.text.toString()
            val guide_cpw = binding.edtGuideconfirmpassword.text.toString()

            if (guide_name.isNotEmpty() && guide_email.isNotEmpty() && guide_phone.isNotEmpty() && guide_pw.isNotEmpty() && guide_cpw.isNotEmpty()) {
                if (imageUri != null){
                    uploadImageToFirebaseStorage(guide_name, guide_email, guide_phone, guide_pw, guide_cpw)
                }else{
                    Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.GuideSignInRedirectBtn.setOnClickListener {
            GuideSignInDirect()
        }

    }

    // Function to open image chooser
    private fun openImageChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST)
    }

    // Handling image selection result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            imageView.setImageURI(imageUri) // Display selected image in ImageView
        }
    }

    // Function to upload the image to Firebase Storage
    private fun uploadImageToFirebaseStorage(
        guide_name: String,
        guide_email: String,
        guide_phone: String,
        guide_pw: String,
        guide_cpw: String
    ) {
        // Create a reference to the location where the image will be stored
        val storageReference = storageRef.getReference("guide_images/${UUID.randomUUID()}.jpg")

        // Upload the image to Firebase Storage
        storageReference.putFile(imageUri!!)
            .addOnSuccessListener { taskSnapshot ->
                // Get the download URL of the uploaded image
                storageReference.downloadUrl.addOnSuccessListener { uri ->
                    // After successful image upload, proceed with user sign-up and data storage
                    val imageUrl = uri.toString()
                    signUpGuides(guide_name, guide_email, guide_phone, guide_pw, guide_cpw, imageUrl)
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Image Upload Failed: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun signUpGuides(
        name: String,
        email: String,
        phone: String,
        pw: String,
        cpw: String,
        imageUrl: String
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, pw)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val uid = firebaseAuth.currentUser?.uid
                    if (uid != null) {
                        addGuideToDatabase(name, email, uid, pw, phone, imageUrl)
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

    private fun addGuideToDatabase(
        name: String,
        email: String,
        uid: String,
        pw: String,
        phone: String,
        imageUrl: String,
    ) {
        val guides = Guide(name, email, uid, pw, phone, imageUrl)
        databaseRef.child("Guides").child(uid).setValue(guides)
    }

    private fun signupsuccess() {
        val intent = Intent(this, GuideHome::class.java)
        startActivity(intent)
        finish()
    }


    fun GuideSignInDirect() {
        val intent = Intent(this, SignInGuide::class.java)
        startActivity(intent)
        finish()
    }
}