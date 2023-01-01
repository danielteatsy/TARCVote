package my.edu.tarc.tarcvote.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import my.edu.tarc.tarcvote.R
import my.edu.tarc.tarcvote.data.Candidate

class ResultAdapter(
    context: Context,
    candidates: List<Candidate>
) : ArrayAdapter<Candidate>(context, 0, candidates) {

   /* override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val candidate = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.items_result, parent, false)
        val nameTextView = view.findViewById<TextView>(R.id.ResultName)
        if (candidate != null) {
            nameTextView.text = candidate.name
        }
        val voteTextView = view.findViewById<TextView>(R.id.ResultNum)
        if (candidate != null) {
            voteTextView.text = candidate.votes.toString()
        }
        return view
    }*/
}