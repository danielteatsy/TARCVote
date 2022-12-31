package my.edu.tarc.tarcvote.ui.admin

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import my.edu.tarc.tarcvote.R
import my.edu.tarc.tarcvote.data.Candidate
import java.util.*

class CreateCandidateActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var button: Button
    private lateinit var submitBtn: Button
    private lateinit var candidateName: TextInputEditText
    private lateinit var candidateDesc: TextInputEditText
    private lateinit var db : FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    val IMAGE_REQUEST_CODE = 1


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_candidate)



        candidateName = findViewById(R.id.candidate_name_edit_text)
        candidateDesc = findViewById(R.id.candidate_descText)
        submitBtn = findViewById(R.id.submit_candidate_button)

        db = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

            submitBtn.setOnClickListener {
                // Retrieve the image URL from the imageView
                val imageUrl = imageView.drawable.toString()
                    saveImageUrlInFirestore(imageUrl)


            }


        // Add image profile candidate
        button = findViewById(R.id.btnUploadPhoto)
        imageView = findViewById(R.id.candidate_image)

        button.setOnClickListener{
            pickImageGallery()
        }
    }

    private fun pickImageGallery() {

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == IMAGE_REQUEST_CODE) {
            val imageUri = data?.data
            Glide.with(this@CreateCandidateActivity)
                .load(imageUri)
                .into(imageView)
            uploadImageToFirebase(data?.data!!)
        }
    }

    private fun uploadImageToFirebase(imageUri: Uri) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("candidate_images/${UUID.randomUUID()}")

        val uploadTask = imageRef.putFile(imageUri)
        uploadTask.addOnSuccessListener {
            // Image was successfully uploaded to Firebase Storage
            imageRef.downloadUrl.addOnSuccessListener {
                // Image URL was successfully retrieved
                val imageUrl = it.toString()
                // Save the image URL in Cloud Firestore
                saveImageUrlInFirestore(imageUrl)
            }
        }.addOnFailureListener {
            // An error occurred while uploading the image
            Toast.makeText(this, "Error uploading image: ${it.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun  saveImageUrlInFirestore(imageUrl: String) {
        val candidateName = candidateName.text.toString()
        val candidateDescription = candidateDesc.text.toString()
        val id = UUID.randomUUID().toString()
        val votes = 0


        /*if (candidateName.isEmpty() || candidateDescription.isEmpty()) {
            Toast.makeText(
                this,
                "Candidate name and description cannot be empty",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            *//*val candidate = Candidate(id, candidateName, candidateDescription, imageUrl, votes)*//*
            val candidateRef = db.collection("candidates").document()

            // Set the candidate ID and save the candidate to Cloud Firestore
            candidateRef.set(candidate, SetOptions.merge())
                .addOnSuccessListener {
                    // Candidate was successfully updated in Cloud Firestore
                    Toast.makeText(this, "Candidate create successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    // An error occurred while updating the candidate in Cloud Firestore
                    Toast.makeText(this, "Error create candidate: ${it.message}", Toast.LENGTH_LONG)
                        .show()
                }
        }*/
    }



}