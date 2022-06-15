package ac.ic.bookapp.data

import ac.ic.bookapp.model.Book
import kotlinx.coroutines.runBlocking
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

object BookDatasource : Datasource<BookService>(BookService::class.java) {

    fun getBooks(): List<Book> {
        return runBlocking {
            service.getBooks()
        }
    }

    fun postBook(isbn: String) {
        runBlocking {
            service.postBook(isbn)
        }
    }
}

interface BookService {
    @GET("books")
    suspend fun getBooks(): List<Book>

    @POST("books")
    suspend fun postBook(@Body isbn: String)
}