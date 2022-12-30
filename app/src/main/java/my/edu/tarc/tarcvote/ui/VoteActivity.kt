package my.edu.tarc.tarcvote.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import my.edu.tarc.tarcvote.R
import my.edu.tarc.tarcvote.data.Candidate

class VoteActivity : AppCompatActivity() {



    private lateinit var recyclerView: RecyclerView

    private val db = FirebaseFirestore.getInstance()
    private val candidates = mutableListOf<Candidate>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vote)

        recyclerView = findViewById(R.id.recyclerViewVoting)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val votingAdapter = VotingAdapter(candidates, onVote = ::vote)
        recyclerView.adapter = votingAdapter

        // Fetch the candidates from Cloud Firestore
        db.collection("candidates")
            .addSnapshotListener { querySnapshot, exception ->
                if (exception != null) {
                    // An error occurred
                    return@addSnapshotListener
                }

                candidates.clear()
                for (document in querySnapshot!!) {
                    val candidate = document.toObject(Candidate::class.java)
                    candidates.add(candidate)
                }
                votingAdapter.notifyDataSetChanged()
            }
    }

    private fun vote(candidate: Candidate) {
        // Increment the candidate's vote count in Cloud Firestore
        val updatedCandidate = candidate.copy(votes
        = candidate.votes + 1)
        db.collection("candidates").document(candidate.id).set(updatedCandidate)
            .addOnSuccessListener {
// Vote successful, show a message or do something else here
                Toast.makeText(this, "Vote successful", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
// Vote failed, show a message or do something else here
                Toast.makeText(this, "Vote failed: ${it.message}", Toast.LENGTH_LONG).show()
            }
    }
}



