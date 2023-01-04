package my.edu.tarc.tarcvote.ui.organiser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import my.edu.tarc.tarcvote.R
import my.edu.tarc.tarcvote.data.Campaign

class EditCampaignActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_campaign)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Edit Campaign"

        val campaign = intent.getParcelableExtra<Campaign>("CAMPAIGN_DATA")





    }


}