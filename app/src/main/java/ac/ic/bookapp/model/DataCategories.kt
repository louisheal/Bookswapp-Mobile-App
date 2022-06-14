package ac.ic.bookapp.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class Book(
    @field:Json(name = "isbn") val isbn: String,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "published") val published: String
)

data class LibBook(
    val title: String
)

data class Isbn(
    val isbn: String
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