package my.edu.tarc.tarcvote.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import my.edu.tarc.tarcvote.R
import my.edu.tarc.tarcvote.data.Campaign
import my.edu.tarc.tarcvote.data.Vote

class VoteActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var campaign: Campaign


    private lateinit var datetimeTextView: TextView
    private lateinit var titleEditText: TextView

    private lateinit var candidate1Select: RadioButton
    private lateinit var candidate2Select: RadioButton
    private lateinit var candidate3Select: RadioButton

    private lateinit var submit: Button

    private var selectedCandidate: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vote)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Cast Vote"

        // Get a reference to the Firestore database
        db = FirebaseFirestore.getInstance()

        // Get the campaign data from the intent
        campaign = intent.getParcelableExtra("CAMPAIGN_DATA")!!



        titleEditText = findViewById(R.id.textPollTitle)
        datetimeTextView = findViewById(R.id.textPollTime)
        candidate1Select = findViewById(R.id.radioCandidate1)
        candidate2Select = findViewById(R.id.radioCandidate2)
        candidate3Select = findViewById(R.id.radioCandidate3)
        submit = findViewById(R.id.btnSubmit)



        intent.putExtra("CAMPAIGN_ID", campaign.id)
        titleEditText.setText(campaign.title)
        datetimeTextView.text = campaign.endDateTime.toDate().toString()
        candidate1Select.setText(campaign.candidate1.name)
        candidate2Select.setText(campaign.candidate2.name)
        candidate3Select.setText(campaign.candidate3.name)



        submit.setOnClickListener {
            // Get the current user's uid
            val currentUserUid = FirebaseAuth.getInstance().currentUser!!.uid

            // Check if the user has already voted
            db.collection("votes").whereEqualTo("userId", currentUserUid).whereEqualTo("campaignId", campaign.id).get()
                .addOnSuccessListener { querySnapshot ->
                    if (querySnapshot.isEmpty) {
                        // If the user has not already voted, allow them to vote
                        val selectedCandidate: String? = when {
                            candidate1Select.isChecked -> campaign.candidate1.name
                            candidate2Select.isChecked -> campaign.candidate2.name
                            candidate3Select.isChecked -> campaign.candidate3.name
                            else -> {
                                // If no candidate is selected, show an error message
                                Toast.makeText(this, "Please select a candidate.", Toast.LENGTH_SHORT).show()
                                return@addOnSuccessListener
                            }
                        }

                        // Determine the vote number based on the selected candidate
                        val voteNumber = when (selectedCandidate) {
                            campaign.candidate1.name -> 1
                            campaign.candidate2.name -> 2
                            campaign.candidate3.name -> 3
                            else -> 0
                        }

                        // Create a new vote object with the current user's uid, the campaign id, the selected candidate's name, and the vote number
                        val vote = selectedCandidate?.let {
                            Vote(currentUserUid, campaign.id,
                                it, voteNumber)
                        }

                        // Save the vote to the database
                        if (vote != null) {
                            db.collection("votes").add(vote)
                                .addOnSuccessListener {
                                    // If the vote is successfully saved, show a message to the user
                                    Toast.makeText(this, "Vote successfully cast", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener {
                                    // If the vote fails to save, show an error message
                                    Toast.makeText(this, "Error casting vote: $it", Toast.LENGTH_SHORT).show()
                                }
                        }
                    } else {
                        // If the user has already voted, show an error message
                        Toast.makeText(this, "You have already voted in this campaign.", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    // If there is an error querying the votes, show an error message
                    Toast.makeText(this, "Error checking vote history: $it", Toast.LENGTH_SHORT).show()
                }
        }




    }
}



