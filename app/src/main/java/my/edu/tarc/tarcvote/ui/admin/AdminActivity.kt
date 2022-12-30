package my.edu.tarc.tarcvote.ui.admin

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import my.edu.tarc.tarcvote.R
import my.edu.tarc.tarcvote.ui.LoginActivity
import my.edu.tarc.tarcvote.ui.ResultActivity

class AdminActivity : AppCompatActivity() {

    private lateinit var cardLogout: CardView
    private lateinit var createCandidate: CardView
    private lateinit var candidateResult: CardView
    private lateinit var candidateList: CardView
    private lateinit var userNameTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

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

        cardLogout = findViewById(R.id.AdminLogOut)
        createCandidate = findViewById(R.id.CreateCandidate)
        candidateList = findViewById(R.id.CandidateList)
        candidateResult = findViewById(R.id.UserResult)
        userNameTextView = findViewById(R.id.UserTextName)


        cardLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this@AdminActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        createCandidate.setOnClickListener {
            val intent = Intent(this@AdminActivity, CreateCandidateActivity::class.java)
            startActivity(intent)
        }

        candidateList.setOnClickListener{
            val intent = Intent(this@AdminActivity, CandidateListActivity::class.java)
            startActivity(intent)
        }

        candidateResult.setOnClickListener{
            val intent = Intent(this@AdminActivity, ResultActivity::class.java)
            startActivity(intent)
        }




    }
}