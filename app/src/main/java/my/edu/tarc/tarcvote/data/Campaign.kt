package my.edu.tarc.tarcvote.data

import com.google.firebase.Timestamp

data class Campaign(
    val id: String = "",
    val title: String = "",
    val endDateTime: Timestamp = Timestamp.now(),
    val candidate1: Candidate = Candidate(),
    val candidate2: Candidate = Candidate(),
    val candidate3: Candidate = Candidate()
) {

    constructor() : this("", "", Timestamp.now(), Candidate(), Candidate(), Candidate())

}
