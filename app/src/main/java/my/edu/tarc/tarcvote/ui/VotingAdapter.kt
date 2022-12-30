package my.edu.tarc.tarcvote.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.edu.tarc.tarcvote.R
import my.edu.tarc.tarcvote.data.Candidate


class VotingAdapter(
    private val candidates: List<Candidate>,
    private val onVote: (Candidate) -> Unit
) : RecyclerView.Adapter<VotingAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val candidateNameTextView: TextView = itemView.findViewById(R.id.candidateName)
        val candidateDescriptionTextView: TextView = itemView.findViewById(R.id.candidateDesc)
        val candidateImageView: ImageView = itemView.findViewById(R.id.image_candidate)
        val voteButton: Button = itemView.findViewById(R.id.btnVote)

        fun bind(candidate: Candidate) {
            candidateNameTextView.text = candidate.name
            candidateDescriptionTextView.text = candidate.description
            Glide.with(itemView.context)
                .load(candidate.imageUrl)
                .into(candidateImageView)
            voteButton.setOnClickListener {
                onVote(candidate)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vote, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val candidate = candidates[position]
        holder.bind(candidate)
    }

    override fun getItemCount(): Int = candidates.size
}