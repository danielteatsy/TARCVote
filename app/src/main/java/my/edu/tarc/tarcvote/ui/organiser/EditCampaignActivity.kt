package my.edu.tarc.tarcvote.ui.organiser

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
import java.util.*

class EditCampaignActivity : AppCompatActivity() {

    private lateinit var datetimeTextView: TextView
    private lateinit var titleEditText: TextInputEditText
    private lateinit var db: FirebaseFirestore

    private lateinit var candidate1Text: TextInputEditText
    private lateinit var candidate2Text: TextInputEditText
    private lateinit var candidate3Text: TextInputEditText
    private lateinit var editCampaign : Button
    private lateinit var delCampaign: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_campaign)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Edit Campaign"


        titleEditText = findViewById(R.id.textTitle)
        datetimeTextView = findViewById(R.id.DateTime)
        delCampaign = findViewById(R.id.btndelCampaign)
        editCampaign = findViewById(R.id.btnSaveCampaign)
        db = FirebaseFirestore.getInstance()


        candidate1Text = findViewById(R.id.textCandidate1)
        candidate2Text = findViewById(R.id.textCandidate2)
        candidate3Text = findViewById(R.id.textCandidate3)

        // Retrieve the Campaign object from the Intent
        val campaign = intent.getParcelableExtra<Campaign>("CAMPAIGN_DATA")

        // Update the views with the data from the Campaign object
        if (campaign != null) {
            datetimeTextView.text = campaign.endDateTime.toDate().toString()
            titleEditText.setText(campaign.title)
            candidate1Text.setText(campaign.candidate1.name)
            candidate2Text.setText(campaign.candidate2.name)
            candidate3Text.setText(campaign.candidate3.name)
        }

        // Set up the click listener for the save button
        editCampaign.setOnClickListener {
        // Update the fields of the Campaign object
            val updatedTitle = titleEditText.text.toString()
            val updatedCandidate1 = candidate1Text.text.toString()
            val updatedCandidate2 = candidate2Text.text.toString()
            val updatedCandidate3 = candidate3Text.text.toString()
            if (campaign != null) {
                campaign.title = updatedTitle
            }
            if (campaign != null) {
                campaign.candidate1.name = updatedCandidate1
            }
            if (campaign != null) {
                campaign.candidate2.name = updatedCandidate2
            }
            if (campaign != null) {
                campaign.candidate3.name = updatedCandidate3
            }

            // Save the updated data to the database
            if (campaign != null) {
                db.collection("campaigns").document(campaign.toString())
                    .set(campaign)
                    .addOnSuccessListener {
                        // Update successful, show a message to the user
                        Toast.makeText(this, "Campaign updated", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        // Update failed, show an error message
                        Toast.makeText(this, "Error updating campaign: $it", Toast.LENGTH_SHORT).show()
                    }
            }
    }


        // Set up the click listener for the delete button
        delCampaign.setOnClickListener {
// Confirm the delete action with the user
            AlertDialog.Builder(this)
                .setTitle("Delete Campaign")
                .setMessage("Are you sure you want to delete this campaign?")
                .setPositiveButton("Yes") { _, _ ->
// Delete the campaign from the database
                    db.collection("campaigns").document(campaign.toString())
                        .delete()
                        .addOnSuccessListener {
// Delete successful, show a message to the user
                            Toast.makeText(this, "Campaign deleted", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
// Delete failed, show an error message
                            Toast.makeText(this, "Error deleting campaign: $it", Toast.LENGTH_SHORT).show()
                        }

                            // Navigate back to the OrganiserActivity
                            finish()
                }
                .setNegativeButton("No", null)
                .show()
        }


    }
}


