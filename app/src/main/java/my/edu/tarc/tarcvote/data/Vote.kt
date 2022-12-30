package my.edu.tarc.tarcvote.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference

data class Vote(
    val userUid: String,
    val timestamp: Timestamp
)
