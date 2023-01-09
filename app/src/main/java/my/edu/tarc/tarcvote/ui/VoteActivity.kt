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
import my.edu.tarc.tarcvote.data.Candidate
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

        // Set the text of the RadioButtons to the names of the candidates
        candidate1Select.text = campaign.candidate1.name
        candidate2Select.text = campaign.candidate2.name
        candidate3Select.text = campaign.candidate3.name



        submit.setOnClickListener {
            // Get the current user's uid
            val currentUserUid = FirebaseAuth.getInstance().currentUser!!.uid

            // Check if the user has already voted
            FirebaseFirestore.getInstance().collection("votes")
                .whereEqualTo("userId", currentUserUid)
                .whereEqualTo("campaignId", campaign.id)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (querySnapshot.isEmpty) {
                        //If the user has not already voted, allow them to vote
                        val selectedCandidate: Candidate? = when {
                            candidate1Select.isChecked -> campaign.candidate1
                            candidate2Select.isChecked -> campaign.candidate2
                            candidate3Select.isChecked -> campaign.candidate3
                            else -> {
                            // If no candidate is selected, show an error message
                                Toast.makeText(this, "Please select a candidate.", Toast.LENGTH_SHORT).show()
                                return@addOnSuccessListener
                            }
                        }
                        val vote = selectedCandidate?.let {
                            it.name?.let { it1 ->
                                Vote(
                                    currentUserUid,
                                    campaign.id,
                                    it.id,
                                    it1
                                )
                            }
                        }
                        // Add the vote to the Firestore database
                        FirebaseFirestore.getInstance().collection("votes").add(vote!!)
                            .addOnSuccessListener {
                        // If the vote is successfully added, show a success message and finish the activity
                                Toast.makeText(this, "Vote submitted successfully.", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                            .addOnFailureListener {
                            // If there is an error adding the vote, show an error message
                                Toast.makeText(this, "Error submitting vote: $it", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                    // If the user has already voted, show an error message
                        Toast.makeText(this, "You have already voted in this campaign.", Toast.LENGTH_SHORT).show()
                    }
                }


        }




    }
}



