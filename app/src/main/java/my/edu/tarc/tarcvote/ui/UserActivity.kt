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

    private lateinit var cardLogout: CardView
    private lateinit var Voter: CardView
    private lateinit var UserResult: CardView
    private lateinit var userNameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()


        if (userId != null) {
            db.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    val userName = document.getString("name")
                    // Display the user name in the TextView
                    userNameTextView.text = userName
                }
                .addOnFailureListener {
                    // Handle errors here
                }
        }



        cardLogout = findViewById(R.id.LogOut)
        Voter = findViewById(R.id.UserVote)
        userNameTextView = findViewById(R.id.UserTextName)
        UserResult = findViewById(R.id.UserResult)
        /*candidateList = findViewById(R.id.CandidateList)*/

        cardLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this@UserActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        Voter.setOnClickListener {
            val intent = Intent(this@UserActivity, VoteActivity::class.java)
            startActivity(intent)
        }

        UserResult.setOnClickListener{
            val intent = Intent(this@UserActivity, ResultActivity::class.java)
            startActivity(intent)
        }
    }
}