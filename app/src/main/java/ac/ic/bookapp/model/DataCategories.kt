package ac.ic.bookapp.model

data class Book(
    val isbn: String,
    val title: String,
    val published: String
)

data class User(
    val id: String,
    val username: String,
    val passwdHash: String,
    val name: String,
    val email: String,
    val phone: String,
    val joinDate: String
)