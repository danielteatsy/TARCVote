package my.edu.tarc.tarcvote.data

data class Vote(
    val userUId: String,
    val campaignId: String,
    val candidateId: String,
    val voteNumber: Int
)
