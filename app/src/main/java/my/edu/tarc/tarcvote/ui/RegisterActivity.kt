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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()


        val button: Button = findViewById(R.id.btnSigUp)
        val name: TextInputEditText = findViewById(R.id.regName)
        val email: TextInputEditText = findViewById(R.id.regEmail)
        val password: TextInputEditText = findViewById(R.id.regPassword)
        val cPassword: TextInputEditText = findViewById(R.id.regCPassword)
        var isValid = false



        button.setOnClickListener{
            val regName = name.text.toString()
            val regEmail = email.text.toString()
            val regPassword = password.text.toString()
            val regCPassword = cPassword.text.toString()


            if(regName.isEmpty() || regEmail.isEmpty()|| regPassword.isEmpty() || regCPassword.isEmpty()){
                if(regName.isEmpty()){
                    name.error = "Please enter your name"
                }
                if(regEmail.isEmpty()){
                    email.error = "Please enter your email"
                }
                if(regPassword.isEmpty()){
                    password.error = "Please enter your password"
                }
                if(regCPassword.isEmpty()){
                    cPassword.error = "Please re-enter your password"
                }
                Toast.makeText(this@RegisterActivity, "Please enter your info details", Toast.LENGTH_SHORT).show()
            }else if(!regEmail.endsWith("student.tarc.edu.my") && !regEmail.endsWith("student.tarc.edu.my")){
                email.error = "Please enter your TARC email"
                Toast.makeText(this@RegisterActivity, "Please enter your TARC email",Toast.LENGTH_SHORT).show()
            }else if (regPassword.length < 6){
                password.error = "Password must be at least at least 6 characters long"
                Toast.makeText(this@RegisterActivity, "Password must be at least 6 characters long",Toast.LENGTH_SHORT).show()
            }else if (!regPassword.any { it.isUpperCase()}){
                password.error = "Password must contain at least one uppercase character"
                Toast.makeText(this@RegisterActivity, "Password must contain at least one uppercase character",Toast.LENGTH_SHORT).show()
            }else if (!regPassword.any { it.isDigit()}) {
                password.error = "Password must contain at least one digit"
                Toast.makeText(this@RegisterActivity, "Password must contain at least one digit",Toast.LENGTH_SHORT).show()
            }else if (isValid) {

                auth.createUserWithEmailAndPassword(regEmail, regPassword).addOnCompleteListener {
                    val users = hashMapOf(
                        "name" to regName,
                        "email" to regEmail,
                        "uid" to auth.currentUser!!.uid
                    )

                }
            }




        }
    }
}








