package ac.ic.bookapp.data

import ac.ic.bookapp.filesys.LoginPreferences
import ac.ic.bookapp.model.Book
import ac.ic.bookapp.model.Ownership
import ac.ic.bookapp.model.User
import kotlinx.coroutines.runBlocking
import retrofit2.http.*

object BookDatasource : Datasource<BookService>(BookService::class.java) {

    fun getBooks(): List<Book> {
        return runBlocking {
            //TODO: change exceptUID to be current logged in user id
            service.getBooks(0)
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

    fun getOwners(bookId: String): List<User> {
        return runBlocking {
            service.getOwners(bookId)
        }
    }
}

interface BookService {
    @GET("books")
    suspend fun getBooks(@Query("except_uid") exceptUID: Long): List<Book>

    @POST("books")
    suspend fun postBook(@Body isbn: String): Book

    @GET("books/{book_id}/owners")
    suspend fun getOwners(@Path("book_id") bookId: String): List<User>
}