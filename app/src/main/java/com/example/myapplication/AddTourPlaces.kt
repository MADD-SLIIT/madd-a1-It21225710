package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class AddTourPlaces : AppCompatActivity() {

    lateinit var bktotourplacehome: ImageButton

    lateinit var edtaddcity: EditText
    lateinit var edtaddtopic: EditText
    lateinit var edtadddesc: EditText

    private lateinit var chooseImageBtn: Button
    private lateinit var imageViewPreview: ImageView
    private var selectedImageUri: Uri? = null
    private val PICK_IMAGE_REQUEST = 1

    lateinit var addtourplace: Button

    private val firestore = FirebaseFirestore.getInstance()
    private val storageReference = FirebaseStorage.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_tour_places)


        // Initialize UI components
        bktotourplacehome = findViewById(R.id.bckToTourplaceHome)

        addtourplace = findViewById(R.id.tourplaceAddbtn)

        edtaddcity = findViewById(R.id.edtplaceaddselectcity)
        edtaddtopic = findViewById(R.id.edtplaceaddtopic)
        edtadddesc = findViewById(R.id.edtplaceadddesc)

        chooseImageBtn = findViewById(R.id.chooseImageBtn)
        imageViewPreview = findViewById(R.id.imageViewPreview)

        chooseImageBtn.setOnClickListener {
            chooseImage()
        }

        bktotourplacehome.setOnClickListener {
            val intent = Intent(this, GuideHome::class.java)
            startActivity(intent)
        }

        addtourplace.setOnClickListener {
            uploadTourPlace()
        }

    }

    // Function to open image picker
    private fun chooseImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    // Handle image selection
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data
            imageViewPreview.setImageURI(selectedImageUri)
        }
    }

    private fun uploadTourPlace() {
        val city = edtaddcity.text.toString().trim()
        val topic = edtaddtopic.text.toString().trim()
        val description = edtadddesc.text.toString().trim()

        if (city.isEmpty() || topic.isEmpty() || description.isEmpty() || selectedImageUri == null) {
            Toast.makeText(
                this,
                "Please fill in all fields and select an image",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // Upload image to Firebase Storage
        val imageRef = storageReference.child("images/${UUID.randomUUID()}.jpg")
        selectedImageUri?.let { uri ->
            imageRef.putFile(uri).addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    // Once image is uploaded, save the tour place data to Firestore
                    val tourPlace = mapOf(
                        "city" to city,
                        "topic" to topic,
                        "description" to description,
                        "imageUrl" to downloadUri.toString()
                    )

                    firestore.collection("tourPlaces")
                        .add(tourPlace)
                        .addOnSuccessListener {
                            Toast.makeText(
                                this,
                                "Tour place added successfully!",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish() // Close activity after adding the place
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Failed to add tour place", Toast.LENGTH_SHORT)
                                .show()
                        }
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


/*private fun addToFirestore(touristcity: String, touristopics: String,  touristdesc: String) {
    val tourist = hashMapOf(
        "city" to touristcity,
        "topic" to touristopics,
        "description" to touristdesc
    )
    firestore.collection("Tourist Place")
        .add(tourist)
        .addOnSuccessListener { documentReference ->
            Toast.makeText(
                this,
                "Tourist Place added with ID: ${documentReference.id}",
                Toast.LENGTH_SHORT
            ).show()
            // Optionally clear the form fields
            clearForm()

            finish()
        }
        .addOnFailureListener { e ->
            Toast.makeText(this, "Error adding Tourist Place: ${e.message}", Toast.LENGTH_SHORT).show()
        }

}*/

/* private fun clearForm() {
     edtaddcity.text.clear()
     edtaddtopic.text.clear()
     edtadddesc.text.clear()
 }*/