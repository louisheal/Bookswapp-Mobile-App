package ac.ic.bookapp.data

data class User(
    val id: Long,
    val username: String,
    val passwdHash: String,
    val name: String,
    val email: String,
    val phone: String,
    val joinDate: String
)