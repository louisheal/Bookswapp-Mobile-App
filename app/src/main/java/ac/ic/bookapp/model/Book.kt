package ac.ic.bookapp.model

import java.io.Serializable

data class Book(
    val id: Long,
    val isbn: String,
    val title: String,
    val published: String
) : Serializable