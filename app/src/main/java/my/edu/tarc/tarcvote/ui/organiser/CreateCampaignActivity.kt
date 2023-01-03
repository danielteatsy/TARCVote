package my.edu.tarc.tarcvote.ui.organiser

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import my.edu.tarc.tarcvote.R

class CreateCampaignActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_campaign)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Create New Campaign"


        val title : TextInputEditText = findViewById(R.id.textTitle)
        val DateTime: TextView = findViewById(R.id.DateTime)
        val candidate: Button = findViewById(R.id.btnAddCandidate)
        val campaign: Button = findViewById(R.id.btnAddCampaign)

        val db = FirebaseFirestore.getInstance()
        val database = FirebaseStorage.getInstance()












    }

}