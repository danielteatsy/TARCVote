package my.edu.tarc.tarcvote.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import my.edu.tarc.tarcvote.R

class UserActivity : AppCompatActivity() {

    private lateinit var logout: CardView
    private lateinit var voter: CardView
    private lateinit var Result:CardView
    private lateinit var Profile: CardView
    private lateinit var Name: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        supportActionBar?.hide()

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()


        if (userId != null) {
            db.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    val userName = document.getString("name")
                    // Display the user name in the TextView
                    Name.text = userName
                }
                .addOnFailureListener {
                    // Handle errors here
                }
        }



        logout = findViewById(R.id.LogOut)
        voter = findViewById(R.id.UserVote)
        Name = findViewById(R.id.UserTextName)
        Result = findViewById(R.id.UserResult)
        Profile = findViewById(R.id.userProfile)

        logout.setOnClickListener {
            intent.setClass(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }

        voter.setOnClickListener {
            val intent = Intent(this@UserActivity, VoteActivity::class.java)
            startActivity(intent)
        }

        Result.setOnClickListener{
            val intent = Intent(this@UserActivity, ResultActivity::class.java)
            startActivity(intent)
        }

        Profile.setOnClickListener{
            val intent = Intent(this@UserActivity, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}