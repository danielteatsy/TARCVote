package my.edu.tarc.tarcvote.ui.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import my.edu.tarc.tarcvote.R

class AdminResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_result)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Result"


    }
}