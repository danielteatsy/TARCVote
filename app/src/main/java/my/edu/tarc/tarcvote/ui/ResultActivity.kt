package my.edu.tarc.tarcvote.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import my.edu.tarc.tarcvote.R
import my.edu.tarc.tarcvote.data.Campaign


class ResultActivity : AppCompatActivity() {

    private lateinit var campaign: Campaign


    private lateinit var datetimeTextView: TextView
    private lateinit var titleEditText: TextView
    private lateinit var db: FirebaseFirestore



    private lateinit var candidate1Name: TextView
    private lateinit var candidate1Votes: ProgressBar
    private lateinit var candidate2Name: TextView
    private lateinit var candidate2Votes: ProgressBar
    private lateinit var candidate3Name: TextView
    private lateinit var candidate3Votes: ProgressBar
    private lateinit var winnerTextView: TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Result"


        titleEditText = findViewById(R.id.textResult)
        datetimeTextView = findViewById(R.id.textEndTime)
        candidate1Name = findViewById(R.id.candidate1_name)
        candidate1Votes = findViewById(R.id.candidate1_votes)
        candidate2Name = findViewById(R.id.candidate2_name)
        candidate2Votes = findViewById(R.id.candidate2_votes)
        candidate3Name = findViewById(R.id.candidate3_name)
        candidate3Votes = findViewById(R.id.candidate3_votes)
        winnerTextView = findViewById(R.id.winner_text)



        // Get a reference to the Firestore database
        db = FirebaseFirestore.getInstance()


        // Get the campaign data from the intent
        campaign = intent.getParcelableExtra("CAMPAIGN_DATA")!!

        // Set up the views with the campaign data
        titleEditText.setText(campaign.title)
        datetimeTextView.setText(campaign.endDateTime.toDate().toString())
        candidate1Name.text = campaign.candidate1.name
        candidate2Name.text = campaign.candidate2.name
        candidate3Name.text = campaign.candidate3.name





        // Query the "votes" collection for votes with a "campaignId" field that matches the campaign's id
        db.collection("votes")
            .whereEqualTo("campaignId", campaign.id)
            .get()
            .addOnSuccessListener { querySnapshot ->
                // Initialize the vote counts to zero
                var candidate1VoteCount = 0
                var candidate2VoteCount = 0
                var candidate3VoteCount = 0
                // For each vote, check the value of the "candidateId" field and increment the vote count for the corresponding candidate
                for (vote in querySnapshot) {
                    when (vote.getString("candidateId")) {
                        campaign.candidate1.id -> candidate1VoteCount++
                        campaign.candidate2.id -> candidate2VoteCount++
                        campaign.candidate3.id -> candidate3VoteCount++
                    }
                }
                val totalVotes = candidate1VoteCount + candidate2VoteCount + candidate3VoteCount

                // Calculate the percentage of votes each candidate received
                val candidate1VotePercentage = (candidate1VoteCount.toDouble() / totalVotes) * 100
                val candidate2VotePercentage = (candidate2VoteCount.toDouble() / totalVotes) * 100
                val candidate3VotePercentage = (candidate3VoteCount.toDouble() / totalVotes) * 100
                // Set the progress of the ProgressBars to the percentage of votes each candidate received
                candidate1Votes.progress = candidate1VotePercentage.toInt()
                candidate2Votes.progress = candidate2VotePercentage.toInt()
                candidate3Votes.progress = candidate3VotePercentage.toInt()

                // Determine the winner of the campaign
                val winner = when {
                    candidate1VoteCount > candidate2VoteCount && candidate1VoteCount > candidate3VoteCount -> campaign.candidate1
                    candidate2VoteCount > candidate3VoteCount -> campaign.candidate2
                    else -> campaign.candidate3
                }
                // Set the text of the winner TextView to the winner's name
                winnerTextView.text = "The winner is ${winner.name}"
            }






    }
}