package my.edu.tarc.tarcvote.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import my.edu.tarc.tarcvote.R
import my.edu.tarc.tarcvote.data.Campaign
import my.edu.tarc.tarcvote.data.Candidate

class VoteActivity : AppCompatActivity() {

    private lateinit var datetimeTextView: TextView
    private lateinit var titleEditText: TextView

    private lateinit var candidate1Select: RadioButton
    private lateinit var candidate2Select: RadioButton
    private lateinit var candidate3Select: RadioButton
    private lateinit var db: FirebaseFirestore

    private lateinit var submit: Button

    private var selectedCandidate: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vote)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Cast Vote"


        titleEditText = findViewById(R.id.textPollTitle)
        datetimeTextView = findViewById(R.id.textPollTime)
        candidate1Select = findViewById(R.id.radioCandidate1)
        candidate2Select = findViewById(R.id.radioCandidate2)
        candidate3Select = findViewById(R.id.radioCandidate3)
        submit = findViewById(R.id.btnSubmit)

        val campaign = intent.getParcelableExtra<Campaign>("CAMPAIGN_DATA")

        if (campaign != null) {
            intent.putExtra("CAMPAIGN_ID", campaign.id)
        }
        if (campaign != null) {
            titleEditText.setText(campaign.title)
        }
        if (campaign != null) {
            datetimeTextView.text = campaign.endDateTime.toDate().toString()
        }
        if (campaign != null) {
            candidate1Select.setText(campaign.candidate1.name)
        }
        if (campaign != null) {
            candidate2Select.setText(campaign.candidate2.name)
        }
        if (campaign != null) {
            candidate3Select.setText(campaign.candidate3.name)
        }


        submit.setOnClickListener {
            // Get the current user's uid
            val currentUserUid = FirebaseAuth.getInstance().currentUser!!.uid

            // Check if the user has already voted
            if (campaign.nestedObject.vote.contains(currentUserUid)) {
                // If the user has already voted, show an error message
                Toast.makeText(this, "You have already voted in this campaign.", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            // Otherwise, the user can vote
            val selectedCandidate: String? = when {
                candidate1Select.isChecked -> campaign?.candidate1.name
                candidate2Select.isChecked -> campaign?.candidate2?.name
                candidate3Select.isChecked -> campaign?.candidate3.name
                else -> {
                    // If no candidate is selected, show an error message
                    Toast.makeText(this, "Please select a candidate.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            // Create a map of candidates and their vote counts
            val candidates = mapOf(
                campaign.candidate1.name to campaign.candidate1.voteCount,
                campaign.candidate2.name to campaign.candidate2.voteCount,
                campaign.candidate3.name to campaign.candidate3.voteCount
            )

            // Increment the vote count of the selected candidate
            val updatedVoteCount = candidates[selectedCandidate]!! + 1

            // Update the campaign in the database
            try {
                db.collection("campaigns").document(campaign.id)
                    .update(
                        "candidate1.voteCount" to candidates[campaign.candidate1.name],
                        "candidate2.voteCount" to candidates[campaign.candidate2.name],
                        "candidate3.voteCount" to candidates[campaign.candidate3.name],
                        "voters" to campaign.voters
                    )
                    .addOnSuccessListener {
                        // If the update is successful, show a message to the user
                        Toast.makeText(this, "Vote successfully cast", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        // If the update fails, show an error message
                        Toast.makeText(this, "Error casting vote: $it", Toast.LENGTH_SHORT).show()
                    }
            } catch (e: Exception) {
// If an exception is thrown, show an error message
                Toast.makeText(this, "Error casting vote: $e", Toast.LENGTH_SHORT).show()
            }


        }


    }
}



