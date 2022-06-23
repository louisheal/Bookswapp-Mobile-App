package ac.ic.bookapp.model

data class LoanRequest(
    val id: Long,
    val fromUser: User,
    val toUser: User,
    val book: Book,
    val copies: Int,
    val date: String?
)