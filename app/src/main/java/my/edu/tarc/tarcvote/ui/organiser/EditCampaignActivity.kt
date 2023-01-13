package my.edu.tarc.tarcvote.ui.organiser

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import my.edu.tarc.tarcvote.R
import my.edu.tarc.tarcvote.data.Campaign
import my.edu.tarc.tarcvote.data.Candidate
import java.text.SimpleDateFormat
import java.util.*

class EditCampaignActivity : AppCompatActivity() {

    private lateinit var startDateTimeTextView: TextView
    private lateinit var endDateTimeTextView: TextView
    private lateinit var titleEditText: TextInputEditText
    private lateinit var db: FirebaseFirestore

    private lateinit var candidate1Text: TextInputEditText
    private lateinit var candidate2Text: TextInputEditText
    private lateinit var candidate3Text: TextInputEditText
    private lateinit var editCampaign : Button
    private lateinit var delCampaign: Button

    private lateinit var campaign: Campaign


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_campaign)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Edit Campaign"


        titleEditText = findViewById(R.id.textTitle)
        startDateTimeTextView = findViewById(R.id.start_date_time)
        endDateTimeTextView = findViewById(R.id.end_date_time)
        delCampaign = findViewById(R.id.btndelCampaign)
        editCampaign = findViewById(R.id.btnSaveCampaign)
        db = FirebaseFirestore.getInstance()


        candidate1Text = findViewById(R.id.textCandidate1)
        candidate2Text = findViewById(R.id.textCandidate2)
        candidate3Text = findViewById(R.id.textCandidate3)

        // Get the campaign data from the intent
        campaign = intent.getParcelableExtra("CAMPAIGN_DATA")!!

        // Set the text of the EditTexts to the current data of the campaign
        titleEditText.setText(campaign.title)
        startDateTimeTextView.setText(campaign.startDateTime.toDate().toString())
        endDateTimeTextView.setText(campaign.endDateTime.toDate().toString())
        candidate1Text.setText(campaign.candidate1.name)
        candidate2Text.setText(campaign.candidate2.name)
        candidate3Text .setText(campaign.candidate3.name)

        // Set up the delete button's onClickListener
        delCampaign.setOnClickListener {
            // Show a confirmation dialog before deleting the campaign
            AlertDialog.Builder(this)
                .setTitle("Delete Campaign")
                .setMessage("Are you sure you want to delete this campaign?")
                .setPositiveButton("Delete") { _, _ ->
                    // Delete the campaign from the Firestore database
                    db.collection("campaigns").document(campaign.id).delete()
                        .addOnSuccessListener {
                            Toast.makeText(this, "Campaign deleted successfully!", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error deleting campaign!", Toast.LENGTH_SHORT).show()
                        }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        editCampaign.setOnClickListener {
            // Get the updated data from the views
            val title = titleEditText.text.toString()
            val startDate =startDateTimeTextView.text.toString()
            val endDate = endDateTimeTextView.text.toString()
            val candidate1 =  candidate1Text.text.toString()
            val candidate2 = candidate2Text.text.toString()
            val candidate3 = candidate3Text .text.toString()
            if (title.isEmpty() || startDate.isEmpty() || endDate.isEmpty() || candidate1.isEmpty() || candidate2.isEmpty() || candidate3.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isEndTimeValid(startDate, endDate)) {
                Toast.makeText(this, "End time must be after start time!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create a new Campaign object with the updated data
            val updatedCampaign = Campaign(
                id = campaign.id,
                title = title,
                startDateTime = Timestamp.now(),
                endDateTime = Timestamp.now(),
                candidate1 = Candidate(name = candidate1),
                candidate2 = Candidate(name = candidate2),
                candidate3 = Candidate(name = candidate3)
            )

            // Update the campaign in the Firestore database
            db.collection("campaigns").document(campaign.id).set(updatedCampaign)
                .addOnSuccessListener {
                    Toast.makeText(this, "Campaign updated successfully!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error updating campaign!", Toast.LENGTH_SHORT).show()
                }
        }


    }
    private fun isEndTimeValid(startDateTime: String, endDateTime: String): Boolean {
    // Code to check if the end time is valid (i.e. after the start time)
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val startDate = sdf.parse(startDateTime)
        val endDate = sdf.parse(endDateTime)
        return endDate.after(startDate)
    }
}



