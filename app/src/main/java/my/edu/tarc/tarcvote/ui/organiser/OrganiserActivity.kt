package my.edu.tarc.tarcvote.ui.organiser


import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import my.edu.tarc.tarcvote.R
import my.edu.tarc.tarcvote.data.Campaign
import my.edu.tarc.tarcvote.ui.LoginActivity
import my.edu.tarc.tarcvote.ui.ResultActivity


class OrganiserActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    private lateinit var campaignRecyclerView: RecyclerView
    private lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organiser)

        campaignRecyclerView = findViewById(R.id.campaignView)
        campaignRecyclerView.layoutManager = LinearLayoutManager(this)

        // Get a reference to the Firestore database
        db = FirebaseFirestore.getInstance()



        //Navigation Drawer
        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.navHead)

            toggle = ActionBarDrawerToggle(
                this@OrganiserActivity,
                drawerLayout,
                R.string.open,
                R.string.close
            )

            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            navView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.nav_poll -> intent.setClass(this@OrganiserActivity, OrganiserActivity::class.java)
                    R.id.nav_results -> intent.setClass(this@OrganiserActivity, ResultActivity::class.java)
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
            val intent = Intent (this@OrganiserActivity, CreateCampaignActivity::class.java)
            startActivity(intent)
        }



        //

        db.collection("campaigns")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    Log.w(TAG, "Error getting campaigns from Firestore", exception)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val campaigns = snapshot.documents.map { it.toObject(Campaign::class.java) }
                    campaignRecyclerView.adapter = CampaignAdapter(campaigns as List<Campaign>)
                }
            }


        }


    // Adapter for the campaign data
    inner class CampaignAdapter(private val campaigns: List<Campaign>) : RecyclerView.Adapter<CampaignViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampaignViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.items_campaign, parent, false)
            return CampaignViewHolder(view)
        }

        override fun getItemCount(): Int = campaigns.size

        override fun onBindViewHolder(holder: CampaignViewHolder, position: Int) {
            val campaign = campaigns[position]
            holder.titleTextView.text = campaign.title
            holder.timestampTextView.text = campaign.endDateTime.toDate().toString()

            holder.itemView.setOnClickListener {
                // Start the EditCampaignActivity and pass the campaign data as an extra
                val intent = Intent(this@OrganiserActivity, EditCampaignActivity::class.java)
                intent.putExtra("CAMPAIGN_DATA", campaign)
                startActivity(intent)
            }



        }
    }
    // ViewHolder for the campaign data
    inner class CampaignViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.textTitle)
        val timestampTextView: TextView = view.findViewById(R.id.textTimeStamp)
        

    }








    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }



    }

