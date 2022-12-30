package my.edu.tarc.tarcvote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.firebase.firestore.FirebaseFirestore

class ResultActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val listView = findViewById<ListView>(R.id.ResultListView)
        val db = FirebaseFirestore.getInstance()
        val candidates = mutableListOf<Candidate>()

        val resultAdapter = ResultAdapter(this, candidates)
        listView.adapter = resultAdapter

        // Fetch the candidates from Cloud Firestore
        db.collection("candidates")
            .addSnapshotListener { querySnapshot, exception ->
                if (exception != null) {
                    // An error occurred
                    return@addSnapshotListener
                }

                candidates.clear()
                for (document in querySnapshot!!) {
                    val candidate = document.toObject(Candidate::class.java)
                    candidates.add(candidate)
                }
                resultAdapter.notifyDataSetChanged()
            }

    }
}