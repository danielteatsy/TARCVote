package my.edu.tarc.tarcvote.data

import com.google.firebase.Timestamp

data class Campaign(
    val id : String,
    val name: String,
    val endDateTime: Timestamp,
    val candidateList: List<Candidate>,
    val createdAt: Timestamp,
    val updatedAt: Timestamp,
)
