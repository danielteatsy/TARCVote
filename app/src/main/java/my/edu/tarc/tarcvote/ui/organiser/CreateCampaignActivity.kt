package my.edu.tarc.tarcvote.ui.organiser

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import my.edu.tarc.tarcvote.R
import my.edu.tarc.tarcvote.data.Campaign
import my.edu.tarc.tarcvote.data.Candidate
import java.io.ByteArrayOutputStream
import java.nio.file.attribute.FileTime.fromMillis
import java.text.SimpleDateFormat
import java.util.*

class CreateCampaignActivity : AppCompatActivity() {

    private lateinit var datetimeTextView: TextView
    private lateinit var titleEditText: TextInputEditText
    private lateinit var db: FirebaseFirestore

    private lateinit var candidate1Text: TextInputEditText
    private lateinit var candidate2Text: TextInputEditText
    private lateinit var candidate3Text: TextInputEditText
    private lateinit var addCampaignButton : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_campaign)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Create New Campaign"

        titleEditText = findViewById(R.id.textTitle)
        datetimeTextView = findViewById(R.id.DateTime)
        addCampaignButton = findViewById(R.id.btnAddCampaign)

        candidate1Text = findViewById(R.id.textCandidate1)
        candidate2Text = findViewById(R.id.textCandidate2)
        candidate3Text = findViewById(R.id.textCandidate3)

        db = FirebaseFirestore.getInstance()


        addCampaignButton.setOnClickListener {
            val title = titleEditText.text.toString().trim()
            val datetime = datetimeTextView.text.toString().trim()
            val candidate1Name = candidate1Text.text.toString().trim()
            val candidate2Name = candidate2Text.text.toString().trim()
            val candidate3Name = candidate3Text.text.toString().trim()

            if (title.isEmpty() || datetime.isEmpty() || candidate1Name.isEmpty() || candidate2Name.isEmpty() || candidate3Name.isEmpty()) {
                // Display warning message or toast
            } else {
                // Create a new Campaign object
                val campaign = Campaign(
                    id = UUID.randomUUID().toString(),
                    title = title,
                    endDateTime = Timestamp.now(),
                    candidate1 = Candidate(id = UUID.randomUUID().toString(), name = candidate1Name),
                    candidate2 = Candidate(id = UUID.randomUUID().toString(), name = candidate2Name),
                    candidate3 = Candidate(id = UUID.randomUUID().toString(), name = candidate3Name)
                )
                // Add the campaign to the Firestore database
                db.collection("campaigns")
                    .add(campaign)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "Campaign successfully added to Firestore with ID: ${documentReference.id}"
                        )
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding campaign to Firestore", e)
                    }
            }
        }

        //Create DateTime using Widget calendar
        datetimeTextView = findViewById(R.id.DateTime)
        datetimeTextView.setOnClickListener {
            showDateTimeDialog()
        }


    }



    private fun showDateTimeDialog() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                showTimeDialog(calendar)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun showTimeDialog(calendar: Calendar) {
        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)

                // Format the date and time and set it to the TextView
                val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                datetimeTextView.text = formatter.format(calendar.time)
            },calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
        timePickerDialog.show()
    }





}

