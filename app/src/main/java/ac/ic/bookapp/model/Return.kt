package ac.ic.bookapp.model

data class Return(
    val id: Long,
    val loan: Loan,
    val copies: Int
)