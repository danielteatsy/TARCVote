package my.edu.tarc.tarcvote.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference

data class Vote(
    val userId: String,
    val campaignId: String,
    val candidateId: String
)
