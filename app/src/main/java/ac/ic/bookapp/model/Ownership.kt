package ac.ic.bookapp.model

data class Ownership(
    val id: Long,
    val owner: User,
    val book: Book,
    val totalCopies: Int,
    val currentCopies: Int
)