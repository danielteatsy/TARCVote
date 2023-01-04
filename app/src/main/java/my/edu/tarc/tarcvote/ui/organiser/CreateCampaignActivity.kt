package my.edu.tarc.tarcvote.ui.organiser

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Timestamp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.internal.Util.parseDateTime
import com.google.type.DateTime
import my.edu.tarc.tarcvote.R
import my.edu.tarc.tarcvote.data.Campaign
import my.edu.tarc.tarcvote.data.Candidate
import java.nio.file.attribute.FileTime.fromMillis
import java.text.SimpleDateFormat
import java.util.*

class CreateCampaignActivity : AppCompatActivity() {

    private lateinit var datetimeTextView: TextView
    private lateinit var titleEditText: TextInputEditText
    private lateinit var candidateListView: ListView
    private lateinit var db: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    private lateinit var arrayAdapter: ArrayAdapter<Candidate>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_campaign)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Create New Campaign"

        titleEditText = findViewById(R.id.textTitle)
        datetimeTextView = findViewById(R.id.DateTime)
        candidateListView = findViewById(R.id.candidateView)
        db = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()





        //Create DateTime using Widget calendar
        datetimeTextView = findViewById(R.id.DateTime)
        datetimeTextView.setOnClickListener {
            showDateTimeDialog()
        }


        // Set up the "Add Candidate" button click listener


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

