package my.edu.tarc.tarcvote

data class Candidate(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val votes: Int

){
    constructor(): this("", "", "", "", 0)
}


