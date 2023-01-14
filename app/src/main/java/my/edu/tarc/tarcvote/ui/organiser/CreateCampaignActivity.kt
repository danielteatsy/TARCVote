package my.edu.tarc.tarcvote.ui.organiser


import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import java.text.SimpleDateFormat
import java.util.*

class CreateCampaignActivity : AppCompatActivity() {

    private lateinit var startDateTimeTextView: TextView
    private lateinit var endDateTimeTextView: TextView
    private lateinit var titleEditText: TextInputEditText
    private lateinit var db: FirebaseFirestore

    private lateinit var candidate1Text: TextInputEditText
    private lateinit var candidate2Text: TextInputEditText
    private lateinit var candidate3Text: TextInputEditText
    private lateinit var addCampaignButton : Button

    private var campaignId: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_campaign)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Create New Campaign"

        titleEditText = findViewById(R.id.textTitle)
        startDateTimeTextView = findViewById(R.id.start_date_time)
        endDateTimeTextView = findViewById(R.id.end_date_time)
        addCampaignButton = findViewById(R.id.btnAddCampaign)

        candidate1Text = findViewById(R.id.textCandidate1)
        candidate2Text = findViewById(R.id.textCandidate2)
        candidate3Text = findViewById(R.id.textCandidate3)

        db = FirebaseFirestore.getInstance()

        // Retrieve campaign ID from Intent
        campaignId = intent.getStringExtra("campaignId")
        if (campaignId.isNullOrEmpty()) {
            campaignId = UUID.randomUUID().toString()
        }

        addCampaignButton.setOnClickListener {
            val title = titleEditText.text.toString().trim()
            val startDateTime = startDateTimeTextView.text.toString().trim()
            val endDateTime = endDateTimeTextView.text.toString().trim()
            val candidate1Name = candidate1Text.text.toString().trim()
            val candidate2Name = candidate2Text.text.toString().trim()
            val candidate3Name = candidate3Text.text.toString().trim()

            if (title.isEmpty() || startDateTime.isEmpty() || endDateTime.isEmpty() || candidate1Name.isEmpty() || candidate2Name.isEmpty() || candidate3Name.isEmpty()) {
                Toast.makeText(this@CreateCampaignActivity, "Please fill up empty fields", Toast.LENGTH_SHORT).show()
            } else if (!isEndTimeValid(startDateTime, endDateTime)) {
                // Show error message or toast indicating that end time must be after start time
                Toast.makeText(this@CreateCampaignActivity, "The end time must be after start time", Toast.LENGTH_SHORT).show()
            } else {
                val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
                val startTimestamp = Timestamp(format.parse(startDateTime)!!)
                val endTimestamp = Timestamp(format.parse(endDateTime)!!)
                campaignId = intent.getStringExtra("campaignId")
                if (campaignId.isNullOrEmpty()) {
                    campaignId = UUID.randomUUID().toString()
                }
                val campaign = Campaign(
                    id = campaignId!!,
                    title = title,
                    startDateTime = startTimestamp,
                    endDateTime = endTimestamp,
                    candidate1 = Candidate(
                        id = UUID.randomUUID().toString(),
                        name = candidate1Name
                    ),
                    candidate2 = Candidate(
                        id =UUID.randomUUID().toString(),
                        name = candidate2Name
                    ),
                    candidate3 = Candidate(id = UUID.randomUUID().toString(), name = candidate3Name)
                )


                db.collection("campaigns").document(campaignId!!).set(campaign)
                    .addOnSuccessListener {
                        Log.d(TAG, "Campaign successfully added to Firestore with ID: $campaignId")
                        Toast.makeText(this@CreateCampaignActivity, "Campaign successfully added", Toast.LENGTH_SHORT).show()
                        // Clear the input fields
                        titleEditText.setText("")
                        startDateTimeTextView.text = ""
                        endDateTimeTextView.text = ""
                        candidate1Text.setText("")
                        candidate2Text.setText("")
                        candidate3Text.setText("")
                    // Navigate back to the previous activity
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding campaign to Firestore", e)
                        Toast.makeText(this@CreateCampaignActivity, "Error adding campaign", Toast.LENGTH_SHORT).show()
                    }
            }

            startDateTimeTextView.setOnClickListener {
                showStartDateTimeDialog()
            }

            endDateTimeTextView.setOnClickListener {
                showEndDateTimeDialog()
            }
        }
    }
    private fun showStartDateTimeDialog() {
        // Code to show the DatePicker and TimePicker dialogs
        val calendar = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                startDateTimeTextView.text =
                    SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(calendar.time)
            }
            TimePickerDialog(
                this@CreateCampaignActivity,
                timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }
        DatePickerDialog(
            this@CreateCampaignActivity,
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun showEndDateTimeDialog() {
        // Code to show the DatePicker and TimePicker dialogs
        val calendar = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                endDateTimeTextView.text =
                    SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(calendar.time)
            }
            TimePickerDialog(
                this@CreateCampaignActivity,
                timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),
                true
            ).show()
        }
        DatePickerDialog(
            this@CreateCampaignActivity,
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun isEndTimeValid(startDateTime: String, endDateTime: String): Boolean {
        // Code to check if the end time is valid (i.e. after the start time)
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val startDate = sdf.parse(startDateTime)
        val endDate = sdf.parse(endDateTime)
        return endDate?.after(startDate) ?: false
    }

    companion object {
        private const val TAG = "CreateCampaignActivity"
    }
}





















