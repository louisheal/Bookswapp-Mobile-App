package ac.ic.bookapp.model

data class Loan(
    val id: Long,
    val fromUser: User,
    val toUser: User,
    val book: Book,
    val copies: Int,
    val date: String?
)