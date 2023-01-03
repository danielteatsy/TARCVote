package my.edu.tarc.tarcvote.ui


import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import my.edu.tarc.tarcvote.R
import my.edu.tarc.tarcvote.ui.organiser.OrganiserActivity

class LoginActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        val signUp: TextView = findViewById(R.id.textSignUp)

        signUp.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }





    }


}

