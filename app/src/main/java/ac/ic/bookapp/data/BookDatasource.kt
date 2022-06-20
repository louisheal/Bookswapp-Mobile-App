package ac.ic.bookapp.data

import ac.ic.bookapp.model.Book
import ac.ic.bookapp.model.Ownership
import kotlinx.coroutines.runBlocking
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

object BookDatasource : Datasource<BookService>(BookService::class.java) {

    fun getBooks(): List<Book> {
        return runBlocking {
            service.getBooks()
        }
    }

    fun postBook(isbn: String): Book? {
        return runBlocking {
            try {
                service.postBook(isbn)
            } catch (e: Exception) {
                null
            }
        }
    }

    fun getOwners(bookId: String) {
        return runBlocking {
            service.getOwners(bookId)
        }
    }
}

interface BookService {
    @GET("books")
    suspend fun getBooks(): List<Book>

    @POST("books")
    suspend fun postBook(@Body isbn: String): Book

    @GET("books/{book_id}/owners")
    suspend fun getOwners(@Path("book_id") bookId: String): List<Ownership>
}