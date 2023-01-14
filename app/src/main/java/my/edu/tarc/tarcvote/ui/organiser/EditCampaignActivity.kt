package my.edu.tarc.tarcvote.ui.organiser


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
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
    private lateinit var saveButton : Button
    private lateinit var deleteButton: Button
    private lateinit var campaign: Campaign



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_campaign)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Edit Campaign"


        titleEditText = findViewById(R.id.textTitle)
        startDateTimeTextView = findViewById(R.id.start_date_time)
        endDateTimeTextView = findViewById(R.id.end_date_time)
        deleteButton = findViewById(R.id.btndelCampaign)
        saveButton = findViewById(R.id.btnSaveCampaign)
        db = FirebaseFirestore.getInstance()


        candidate1Text = findViewById(R.id.textCandidate1)
        candidate2Text = findViewById(R.id.textCandidate2)
        candidate3Text = findViewById(R.id.textCandidate3)

        setContentView(R.layout.activity_edit_campaign)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Edit Campaign"

        titleEditText = findViewById(R.id.textTitle)
        startDateTimeTextView = findViewById(R.id.start_date_time)
        endDateTimeTextView = findViewById(R.id.end_date_time)
        deleteButton = findViewById(R.id.btndelCampaign)
        saveButton = findViewById(R.id.btnSaveCampaign)
        db = FirebaseFirestore.getInstance()

        candidate1Text = findViewById(R.id.textCandidate1)
        candidate2Text = findViewById(R.id.textCandidate2)
        candidate3Text = findViewById(R.id.textCandidate3)

        // Retrieve the campaign object passed in the intent
        campaign = intent.getParcelableExtra("CAMPAIGN_DATA")!!

        // Populate the views with the campaign's data
        titleEditText.setText(campaign.title)
        startDateTimeTextView.text = campaign.startDateTime.toDate().toString()
        endDateTimeTextView.text = campaign.endDateTime.toDate().toString()
        candidate1Text.setText(campaign.candidate1.name)
        candidate2Text.setText(campaign.candidate2.name)
        candidate3Text.setText(campaign.candidate3.name)


        saveButton.setOnClickListener {
            updateCampaign()
        }

        deleteButton.setOnClickListener {
            deleteCampaign()
        }


    }
    private fun updateCampaign() {
        // Update the campaign's properties with the new data entered by the user
        campaign.title = titleEditText.text.toString()
        campaign.candidate1.name = candidate1Text.text.toString()
        campaign.candidate2.name = candidate2Text.text.toString()
        campaign.candidate3.name = candidate3Text.text.toString()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Update")
        builder.setMessage("Are you sure you want to update this campaign?")
        builder.setPositiveButton("Update") { _, _ ->
            // Update the campaign in the Firebase Firestore database
            db.collection("campaigns").document(campaign.id).set(campaign)
                .addOnSuccessListener {
                    Toast.makeText(this, "Campaign updated successfully", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to update campaign", Toast.LENGTH_SHORT).show()
                }
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun deleteCampaign() {
        // Show confirmation dialog
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Are you sure you want to delete this campaign?")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ ->
        // Delete the campaign from the Firebase Firestore database
                db.collection("campaigns").document(campaign.id).delete()
                    .addOnSuccessListener {
                        Toast.makeText(this, "Campaign deleted successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed to delete campaign", Toast.LENGTH_SHORT).show()
                    }
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
            }
        val alert = dialogBuilder.create()
        alert.setTitle("Confirm")
        alert.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}



