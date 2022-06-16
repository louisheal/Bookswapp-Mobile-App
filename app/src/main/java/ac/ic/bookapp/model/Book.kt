package ac.ic.bookapp.model

import com.squareup.moshi.Json

data class Book(
    @field:Json(name = "id") val id: Long,
    @field:Json(name = "isbn") val isbn: String,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "published") val published: String,

    // TODO: replace placeholder with actual images
    val url: String = "https://cdn.pixabay.com/photo/2018/01/17/18/43/book-3088775_960_720.jpg"
)