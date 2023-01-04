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


}