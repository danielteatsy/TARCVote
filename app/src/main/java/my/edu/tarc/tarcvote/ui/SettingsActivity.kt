package my.edu.tarc.tarcvote.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import my.edu.tarc.tarcvote.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.hide()
    }
}