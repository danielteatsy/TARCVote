package my.edu.tarc.tarcvote.ui.organiser

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import my.edu.tarc.tarcvote.R
import my.edu.tarc.tarcvote.data.Campaign
import my.edu.tarc.tarcvote.ui.ResultActivity

class OrganiserResultList : AppCompatActivity() {

    private lateinit var campaignRecyclerView: RecyclerView
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organizer_result_list)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Result list"

        campaignRecyclerView = findViewById(R.id.resultListView)
        campaignRecyclerView.layoutManager = LinearLayoutManager(this)

        // Get a reference to the Firestore database
        db = FirebaseFirestore.getInstance()

        db.collection("campaigns")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    Log.w(ContentValues.TAG, "Error getting campaigns from Firestore", exception)
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
                val intent = Intent(this@OrganiserResultList, ResultActivity::class.java)
                intent.putExtra("CAMPAIGN_DATA", campaign)
                intent.putExtra("CAMPAIGN_ID", campaign.id)
                startActivity(intent)
            }



        }
    }
    // ViewHolder for the campaign data
    inner class CampaignViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.textTitle)
        val timestampTextView: TextView = view.findViewById(R.id.textTimeStamp)


    }
}