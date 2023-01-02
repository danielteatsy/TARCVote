package my.edu.tarc.tarcvote.ui.admin


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import my.edu.tarc.tarcvote.R
import my.edu.tarc.tarcvote.ui.LoginActivity
import my.edu.tarc.tarcvote.ui.ProfileActivity
import my.edu.tarc.tarcvote.ui.ResultActivity

class AdminActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        //Navigation Drawer
        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.navHead)

            toggle = ActionBarDrawerToggle(
                this@AdminActivity,
                drawerLayout,
                R.string.open,
                R.string.close
            )

            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            navView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.nav_poll -> intent.setClass(this@AdminActivity, AdminActivity::class.java)
                    R.id.nav_results -> intent.setClass(this@AdminActivity, ResultActivity::class.java)
                    R.id.nav_profile -> intent.setClass(this@AdminActivity, ProfileActivity::class.java)
                    R.id.action_logout -> {
                        val preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)
                        preferences.edit().clear().apply()

                        // Navigate to the login screen
                        intent.setClass(this, LoginActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                        finish()
                    }
                }
                startActivity(intent)
                true
            }

        // Button to create poll
        val button : FloatingActionButton = findViewById(R.id.fab)

        button.setOnClickListener{
            val intent = Intent (this@AdminActivity, CreateCampaignActivity::class.java)
            startActivity(intent)
        }

        




        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }



    }

