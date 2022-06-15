package ac.ic.bookapp.model

import com.squareup.moshi.Json

data class Book(
    @field:Json(name = "id") val id: Long,
    @field:Json(name = "isbn") val isbn: String,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "published") val published: String
)

data class User(
    val id: Long,
    val username: String,
    val passwdHash: String,
    val name: String,
    val email: String,
    val phone: String,
    val joinDate: String
)