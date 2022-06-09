package ac.ic.bookapp

import com.squareup.moshi.Json

data class Book (
    val id: String,
    @Json(name = "img_src") val imgSrcUrl: String
    )