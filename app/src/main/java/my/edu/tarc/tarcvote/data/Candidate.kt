package my.edu.tarc.tarcvote.data

import com.google.firebase.firestore.DocumentReference

data class Candidate(
    val id: String,
    val userRef: DocumentReference,
    val name: String,
    val voteList: List<Vote>,
    val imageUrl: String,
    val votes: Int
)