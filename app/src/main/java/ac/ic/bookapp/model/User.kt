package ac.ic.bookapp.model

data class User(
    val id: Long,
    val username: String,
    val name: String,
    val institution: String,
    val department: String,
    val email: String?,
    val phone: String?,
    val joinDate: String?
)