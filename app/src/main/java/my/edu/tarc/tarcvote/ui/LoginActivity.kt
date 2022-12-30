package my.edu.tarc.tarcvote.ui


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import my.edu.tarc.tarcvote.R
import my.edu.tarc.tarcvote.ui.admin.AdminActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val emailPattern = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        val loginEmail: TextInputEditText = findViewById(R.id.et_userlogin_email)
        val loginPassword: TextInputEditText = findViewById(R.id.et_userlogin_password)
        val loginbtn: Button = findViewById(R.id.btn_login)
        val SignUpbtn: Button = findViewById(R.id.btn_signUp)
        val loginPasswordLayout: TextInputLayout = findViewById(R.id.signInPasswordLayout)

        SignUpbtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        loginbtn.setOnClickListener {
            loginPasswordLayout.isPasswordVisibilityToggleEnabled = true

            val email = loginEmail.text.toString()
            val password = loginPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                if (email.isEmpty()) {
                    loginEmail.error = "Please enter your student email"
                }
                if (password.isEmpty()) {
                    loginPassword.error = "Please enter your password"
                    loginPasswordLayout.isPasswordVisibilityToggleEnabled = false
                }
                Toast.makeText(this@LoginActivity, "Enter valid details", Toast.LENGTH_SHORT).show()
            } else if (!email.matches(emailPattern.toRegex())) {
                loginEmail.error = " Enter valid email address"
                Toast.makeText(this@LoginActivity, "Enter valid email address", Toast.LENGTH_SHORT).show()

            }else if (password.length < 6){
                loginPassword.error = " Enter password more than 6 characters"
                Toast.makeText(this@LoginActivity, "Enter password more than 6 characters", Toast.LENGTH_SHORT).show()

            }else{

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        // Get the current user's ID
                        val currentUserId = auth.currentUser!!.uid
                        // Get a reference to the user document in Cloud Firestore
                        val userDocRef = db.collection("users").document(currentUserId)
                        userDocRef.get().addOnSuccessListener { userDoc ->
                            // Check if the user is an admin
                            if (userDoc.getString("isUser") == "0") {
                                // The user is an admin, start the AdminActivity
                                val intent = Intent(this@LoginActivity, AdminActivity::class.java)
                                startActivity(intent)
                            } else {
                                // The user is a regular user, start the HomeActivity
                                val intent = Intent(this@LoginActivity, UserActivity::class.java)
                                startActivity(intent)
                            }
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "Something went wrong. Please, try again", Toast.LENGTH_SHORT).show()
                    }
                }
                /*auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    if (it.isSuccessful){
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@LoginActivity, "Something went wrong. Please, try again", Toast.LENGTH_SHORT).show()
                    }
                }*/
           }
        }
    }


}

