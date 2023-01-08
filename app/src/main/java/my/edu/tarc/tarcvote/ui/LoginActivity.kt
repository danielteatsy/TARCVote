package my.edu.tarc.tarcvote.ui


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import my.edu.tarc.tarcvote.R
import my.edu.tarc.tarcvote.ui.organiser.OrganiserActivity

class LoginActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        val signUp: TextView = findViewById(R.id.textSignUp)
        val email: TextInputEditText = findViewById(R.id.logEmail)
        val password: TextInputEditText = findViewById(R.id.logPassword)
        val signIn: Button = findViewById(R.id.btnSignIn)

        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()



        signUp.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        signIn.setOnClickListener{
            val logEmail = email.text.toString()
            val logPassword = password.text.toString()

            // Check if the email address is not empty
            if (logEmail.isEmpty()) {
                email.error = "Please enter your email address"
                Toast.makeText(this@LoginActivity, "Please enter your email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Check if the password is not empty
            if (logPassword.isEmpty()) {
                password.error = "Please enter your password"
                Toast.makeText(this@LoginActivity, "Please enter your password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Check if the email address is not a TARC email
            if (!logEmail.endsWith("@tarc.edu.my") && !logEmail.endsWith("@gmail.com")) {
                email.error = "Please enter a valid TARC email"
                Toast.makeText(this@LoginActivity, "Please enter a valid TARC email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Check if the password is not at least 6 characters long
            if (logPassword.length < 6) {
                password.error = "Password must be at least 6 characters long"
                Toast.makeText(this@LoginActivity, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Check if the password does not contain at least one uppercase character
            if (!logPassword.matches(Regex(".*[A-Z].*"))) {
                password.error = "Password must contain at least one uppercase character"
                Toast.makeText(this@LoginActivity, "Password must contain at least one uppercase character", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(logEmail, logPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val currentUser = auth.currentUser
                        if (currentUser?.isEmailVerified == true) {
                            // Email is verified, check user role and redirect to appropriate activity
                            val uid = currentUser.uid
                            db.collection("users").document(uid).get().addOnSuccessListener { user ->
                                if (user.contains("isAdmin") && user["isAdmin"] == "0") {
                                    // User is an admin, start the AdminActivity
                                    val intent = Intent(this@LoginActivity, OrganiserActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else if (user.contains("isUser") && user["isUser"] == "1") {
                                    // User is a regular user, start the UserActivity
                                    val intent = Intent(this@LoginActivity, UserActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                // User is not an admin or a regular user, display an error
                                    Toast.makeText(this@LoginActivity, "Error: User role not recognized", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            // Email is not verified, display a message
                            Toast.makeText(this@LoginActivity, "Please verify your email address first", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Login failed, display a message
                        Toast.makeText(this@LoginActivity, "Password incorrect", Toast.LENGTH_SHORT).show()
                    }
                }
        }





    }


}

