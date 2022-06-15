package ac.ic.bookapp.model

import ac.ic.bookapp.data.Book
import kotlinx.coroutines.runBlocking
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.lang.Exception

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
}

interface BookService {
    @GET("books")
    suspend fun getBooks(): List<Book>

    @POST("books")
    suspend fun postBook(@Body isbn: String): Book
}