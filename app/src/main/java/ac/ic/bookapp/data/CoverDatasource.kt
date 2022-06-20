package ac.ic.bookapp.data

import ac.ic.bookapp.R
import ac.ic.bookapp.model.Book
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.core.net.toUri
import coil.load

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

    fun isEmptyCover(image: Drawable?): Boolean {
        val bitmap = (image as BitmapDrawable).bitmap
        return bitmap.height == 1 && bitmap.width == 1
    }

    fun loadCover(image: ImageView, uri: Uri) {
        image.load(uri) {
            placeholder(R.drawable.ic_book)
            error(R.drawable.ic_book)
            target(
                onStart = { placeholder -> image.setImageDrawable(placeholder) },
                onSuccess = { result ->
                    if (!isEmptyCover(result))
                        image.setImageDrawable(result)
                },
                onError = { error -> image.setImageDrawable(error) }
            )
        }
    }
}

