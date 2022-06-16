package ac.ic.bookapp.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class Book(
    @field:Json(name = "id") var id: String,
    @field:Json(name = "isbn") val isbn: String,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "published") val published: String,

    // TODO: replace placeholder with actual images
    val url: String = "https://cdn.pixabay.com/photo/2018/01/17/18/43/book-3088775_960_720.jpg"
)

@JsonClass(generateAdapter = true)
data class JBook(
    @field:Json(name = "isbn") val isbn: String,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "published") val published: String
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