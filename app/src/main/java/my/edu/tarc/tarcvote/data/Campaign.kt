package my.edu.tarc.tarcvote.data

import com.google.firebase.Timestamp

data class Campaign(
    val id : String,
    val title: String,
    val endDateTime: Timestamp,
    val candidate1: Candidate,
    val candidate2: Candidate,
    val candidate3: Candidate
)
