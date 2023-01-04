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

        intent.getStringExtra("CAMPAIGN_ID")
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
            candidate1Text.setText(campaign.candidate1.name)
        }
        if (campaign != null) {
            candidate2Text.setText(campaign.candidate2.name)
        }
        if (campaign != null) {
            candidate3Text.setText(campaign.candidate3.name)
        }


        //Save campaign have issue
        editCampaign.setOnClickListener {
            // Update the fields of the Campaign object
            val updatedTitle = titleEditText.text.toString()
            val updatedCandidate1 = candidate1Text.text.toString()
            val updatedCandidate2 = candidate2Text.text.toString()
            val updatedCandidate3 = candidate3Text.text.toString()

            campaign?.apply {
                title = updatedTitle
                endDateTime = Timestamp.now()
                candidate1.name = updatedCandidate1
                candidate2.name = updatedCandidate2
                candidate3.name = updatedCandidate3
            }

            if (campaign != null) {
                db.collection("campaigns").document(campaign.id)
                    .update(mapOf(
                        "title" to updatedTitle,
                        "endDateTime" to Timestamp.now(),
                        "candidate1" to mapOf(
                            "name" to updatedCandidate1
                        ),
                        "candidate2" to mapOf(
                            "name" to updatedCandidate2
                        ),
                        "candidate3" to mapOf(
                            "name" to updatedCandidate3
                        )
                    ))
                    .addOnSuccessListener {
                        // Update successful, show a message to the user
                        Toast.makeText(this, "Campaign updated", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        // Update failed, show an error message
                        Toast.makeText(this, "Error updating campaign: $it", Toast.LENGTH_SHORT).show()
                    }
            }
                    // Confirm the edit action with the user
                    AlertDialog.Builder(this)
                        .setTitle("Edit Campaign")
                        .setMessage("Are you sure you want to save the changes to this campaign?")
                        .setPositiveButton("Yes") { _, _ ->
                            // Navigate back to the OrganiserActivity
                            finish()
                        }
                        .setNegativeButton("No", null)
                        .show()
        }


        delCampaign.setOnClickListener {
            // Show a message dialog to confirm the delete action
            AlertDialog.Builder(this)
                .setTitle("Delete Campaign")
                .setMessage("Are you sure you want to delete this campaign?")
                .setPositiveButton("Yes") { _, _ ->
                    if (campaign != null) {
                        deleteCampaign(campaign)
                    }
                }
                .setNegativeButton("No", null)
                .show()
        }



    }

    private fun deleteCampaign(campaign: Campaign) {
        db.collection("campaigns").document(campaign.id).delete()
            .addOnSuccessListener {
                // Navigate back to the OrganiserActivity
                finish()
            }
            .addOnFailureListener { e ->
                // Show an error message
                Toast.makeText(this, "Error deleting campaign: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }






}