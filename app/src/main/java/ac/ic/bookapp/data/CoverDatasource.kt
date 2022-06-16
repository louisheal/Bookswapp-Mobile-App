package ac.ic.bookapp.data

import ac.ic.bookapp.model.Book
import android.net.Uri
import androidx.core.net.toUri

private const val coversUrl = "https://covers.openlibrary.org/b/isbn"

enum class CoverSize(
    private val string: String
) {
    SMALL("S"),
    MEDIUM("M"),
    LARGE("L");

    override fun toString(): String {
        return string
    }
}

object CoverDatasource {

    fun getBookCover(book: Book, size: CoverSize): Uri {
        return "$coversUrl/${book.isbn}-$size.jpg"
            .toUri()
            .buildUpon()
            .scheme("https")
            .build()
    }
}