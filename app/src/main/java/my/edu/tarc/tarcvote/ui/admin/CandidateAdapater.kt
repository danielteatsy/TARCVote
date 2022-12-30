package my.edu.tarc.tarcvote.ui.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import com.google.firebase.storage.FirebaseStorage
import my.edu.tarc.tarcvote.R
import my.edu.tarc.tarcvote.data.Candidate


class CandidateAdapater(private val candidates: List<Candidate>,
                        private val onDelete: (Candidate) -> Unit): RecyclerView.Adapter<CandidateAdapater.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val candidateNameTextView: TextView = itemView.findViewById(R.id.candidateName)
        val candidateImageView: ImageView = itemView.findViewById(R.id.image_candidate)
        val deleteButton: Button = itemView.findViewById(R.id.btnVote)

        fun bind(candidate: Candidate) {

            val storage = FirebaseStorage.getInstance()
            val imageRef = storage.getReference(candidate.imageUrl)

            candidateNameTextView.text = candidate.name
            Glide.with(itemView.context)
                .load(imageRef)
                .into(candidateImageView)
            deleteButton.setOnClickListener {
                onDelete(candidate)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.items_candidate, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val candidate = candidates[position]
        holder.bind(candidate)
    }

    override fun getItemCount(): Int = candidates.size


}