package my.edu.tarc.tarcvote.ui.organiser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import my.edu.tarc.tarcvote.R

class EditCampaignActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_campaign)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Edit Campaign"





    }


}