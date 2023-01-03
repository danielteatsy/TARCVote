package my.edu.tarc.tarcvote.ui.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import my.edu.tarc.tarcvote.R

class CreateCampaignActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_campaign)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Create New Campaign"







    }

}