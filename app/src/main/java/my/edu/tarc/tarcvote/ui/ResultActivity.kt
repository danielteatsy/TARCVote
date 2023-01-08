package my.edu.tarc.tarcvote.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import my.edu.tarc.tarcvote.R
import my.edu.tarc.tarcvote.data.Campaign
import my.edu.tarc.tarcvote.data.Candidate

class ResultActivity : AppCompatActivity() {

    private lateinit var titleEditText: TextView
    private lateinit var datetimeTextView: TextView
    private lateinit var db: FirebaseFirestore
    private lateinit var campaign: Campaign


    private lateinit var candidate1Name: TextView
    private lateinit var candidate1Votes: ProgressBar
    private lateinit var candidate2Name: TextView
    private lateinit var candidate2Votes: ProgressBar
    private lateinit var candidate3Name: TextView
    private lateinit var candidate3Votes: ProgressBar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Result"

        campaign = intent.getParcelableExtra("CAMPAIGN_DATA")!!



        intent.putExtra("CAMPAIGN_ID", campaign.id)
        titleEditText.setText(campaign.title)
        datetimeTextView.text = campaign.endDateTime.toDate().toString()

        // Get a reference to the Firestore database
        db = FirebaseFirestore.getInstance()



        candidate1Name = findViewById(R.id.candidate1_name)
        candidate1Votes = findViewById(R.id.candidate1_votes)
        candidate2Name = findViewById(R.id.candidate2_name)
        candidate2Votes = findViewById(R.id.candidate2_votes)
        candidate3Name = findViewById(R.id.candidate3_name)
        candidate3Votes = findViewById(R.id.candidate3_votes)






    }
}