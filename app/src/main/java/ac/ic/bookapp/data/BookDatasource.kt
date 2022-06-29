package ac.ic.bookapp.data

import ac.ic.bookapp.model.Book
import ac.ic.bookapp.model.User
import kotlinx.coroutines.runBlocking
import retrofit2.http.*

object BookDatasource : AuthenticatedDatasource<BookService>(BookService::class.java) {

    fun getBooks(exceptUser: Long? = null): List<Book> {
        return runBlocking {
            service.getBooks(exceptUser)
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

    fun getOwners(bookId: Long): List<User> {
        return runBlocking {
            service.getOwners(bookId)
        }
    }

    fun getOwnersCount(bookId: Long, exceptUserId: Long): Int {
        return runBlocking {
            service.getOwnersCount(bookId, exceptUserId)
        }
    }
}

interface BookService {
    @GET("books")
    suspend fun getBooks(@Query("exceptUser") exceptUserId: Long?): List<Book>

    @POST("books")
    suspend fun postBook(@Body isbn: String): Book

    @GET("books/{book_id}/owners")
    suspend fun getOwners(@Path("book_id") bookId: Long): List<User>

    @GET("books/{book_id}/owners/count")
    suspend fun getOwnersCount(
        @Path("book_id") bookId: Long,
        @Query("exceptUser") exceptUserId: Long
    ): Int
}