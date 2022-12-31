package my.edu.tarc.tarcvote.ui


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import my.edu.tarc.tarcvote.R


class RegisterActivity : AppCompatActivity(){
    private lateinit var auth: FirebaseAuth
    private lateinit var database : FirebaseDatabase
    private val emailPattern = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        val db = FirebaseFirestore.getInstance()

        val regStudentid: TextInputEditText = findViewById(R.id.et_reg_studentid)
        val regName: TextInputEditText = findViewById(R.id.et_reg_name)
        val regEmail: TextInputEditText = findViewById(R.id.et_reg_email)
        val regPassword: TextInputEditText = findViewById(R.id.et_reg_password)
        val regCPassword: TextInputEditText = findViewById(R.id.et_reg_confirmPassword)
        val regPasswordLayout: TextInputLayout = findViewById(R.id.etPasswordLayout)
        val regCPasswordLayout: TextInputLayout = findViewById(R.id.etCPasswordLayout)
        val regBtn: Button = findViewById(R.id.btn_register)

       // val loginInText : TextView = findViewById(R.id.signInText)
       /* loginInText.setOnClickListener {
            val intent = Intent(this, LoginActivity:: class.java)
            startActivity(intent)
        }*/

        var isValid = false



        regBtn.setOnClickListener {
            val studentID = regStudentid.text.toString()
            val name = regName.text.toString()
            val email = regEmail.text.toString()
            val password = regPassword.text.toString()
            val cPassword = regCPassword.text.toString()

            regPasswordLayout.isPasswordVisibilityToggleEnabled = true
            regCPasswordLayout.isPasswordVisibilityToggleEnabled = true

            if(studentID.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty() || cPassword.isEmpty()){
                if(studentID.isEmpty()){
                    regStudentid.error = "Enter your Student ID"
                }
                if(name.isEmpty()){
                    regName.error = "Enter your name"
                }
                if(email.isEmpty()){
                    regEmail.error = "Enter your email"
                }
                if(password.isEmpty()){
                    regPassword.error = "Enter your password"
                    regPasswordLayout.isPasswordVisibilityToggleEnabled = false
                }
                if(cPassword.isEmpty()){
                    regCPassword.error = "Re-enter your password"
                    regCPasswordLayout.isPasswordVisibilityToggleEnabled = false
                }
                Toast.makeText(this@RegisterActivity, "Enter valid details", Toast.LENGTH_SHORT).show()
            }else if(!email.matches(emailPattern.toRegex())){
                regEmail.error = " Enter valid email address"
                Toast.makeText(this@RegisterActivity, "Enter valid email address", Toast.LENGTH_SHORT).show()
            }else if (password.length < 6){
                regPassword.error = " Enter password more than 6 characters"
                Toast.makeText(this@RegisterActivity, "Enter password more than 6 characters", Toast.LENGTH_SHORT).show()
            }else if(password != cPassword){
                regCPassword.error = "Password not matched, try again"
                Toast.makeText(this@RegisterActivity, "Password not matched, try again", Toast.LENGTH_SHORT).show()
            } else if (isValid) {
            // User has made a valid selection, continue with registration process
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    // Add the new user to Cloud Firestore
                    val db = FirebaseFirestore.getInstance()
                    val users = hashMapOf(
                        "name" to name,
                        "email" to email,
                        "studentID" to studentID,
                        "uid" to auth.currentUser!!.uid
                    )

                    db.collection("users").document(auth.currentUser!!.uid).set(users)
                        .addOnSuccessListener {
                            Toast.makeText(this@RegisterActivity, "Account Created", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this@RegisterActivity, "Something went wrong, try again", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this@RegisterActivity, "Something went wrong, try again", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            // Show an error message to the user
            Toast.makeText(this, "Please select whether you are an admin or a user", Toast.LENGTH_SHORT).show()
        }


        }

    }




}




