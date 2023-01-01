package my.edu.tarc.tarcvote.ui.admin

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import my.edu.tarc.tarcvote.R
import my.edu.tarc.tarcvote.data.Candidate

class EditCampaignActivity : AppCompatActivity() {




    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candidate_list)


        val db = FirebaseFirestore.getInstance()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewCandidates)
        val query = db.collection("candidates").orderBy("name")
        query.get()
            .addOnSuccessListener { snapshot ->
                val candidates = snapshot.toObjects(Candidate::class.java)
                val candidateAdapter = CandidateAdapater(candidates, onDelete = { candidate ->
                    // Delete the candidate from Cloud Firestore
                    val candidateRef = db.collection("candidates").document(candidate.id)
                    // Delete the candidate from Cloud Firestore
                    candidateRef.delete()
                        .addOnSuccessListener {
                            // Candidate document was successfully deleted
                            // Now delete the image from Firebase Storage
                            val storageRef = FirebaseStorage.getInstance().reference
                            val imageRef = storageRef.child(candidate.imageUrl)
                            imageRef.delete()
                                .addOnSuccessListener {
                                    // Image was successfully deleted
                                    Toast.makeText(
                                        this,
                                        "Candidate deleted successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                .addOnFailureListener {
                                    // An error occurred while deleting the image
                                    Toast.makeText(
                                        this,
                                        "Error deleting image: ${it.message}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                        }
                        .addOnFailureListener {
                            // An error occurred while deleting the candidate document
                            Toast.makeText(
                                this,
                                "Error deleting candidate: ${it.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                })
                recyclerView.adapter = candidateAdapter
                recyclerView.layoutManager = LinearLayoutManager(this)
            }


    }


}