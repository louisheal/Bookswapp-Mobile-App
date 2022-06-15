package ac.ic.bookapp.data

import com.squareup.moshi.Json

data class Book(
    @field:Json(name = "id") val id: Long,
    @field:Json(name = "isbn") val isbn: String,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "published") val published: String
)