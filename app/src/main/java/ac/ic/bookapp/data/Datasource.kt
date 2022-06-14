package ac.ic.bookapp.data

import ac.ic.bookapp.model.*
import android.widget.Toast
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

object Datasource {

    fun getBooks(): List<Book> {
        var response: List<Book> = listOf()
        runBlocking {
            try {
                response = BookApi.retrofitService.getBooks()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return response
    }

    fun getUsers(): List<User> {
        var response: List<User> = listOf()
        runBlocking {
            try {
                response = BookApi.retrofitService.getUsers()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return response
    }


    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val retrofitBook = Retrofit.Builder()
        .baseUrl("https://drp19-staging.herokuapp.com/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val retrofitLibrary = Retrofit.Builder()
        .baseUrl("https://openlibrary.org")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    interface BookApiService {
        @GET("books")
        suspend fun getBooks(): List<Book>

        @GET("users")
        suspend fun getUsers(): List<User>

        @POST("books")
        suspend fun postBook(@Body book: Book): Book
    }

    interface LibraryApiService {
        @GET("isbn/{isbn}.json")
        suspend fun lookupIsbn(@Path("isbn") isbn: String): LibBook
    }

    object BookApi {
        val retrofitService: BookApiService by lazy { retrofitBook.create(BookApiService::class.java) }
    }

    object LibraryApi {
        val retrofitService: LibraryApiService by lazy { retrofitLibrary.create(LibraryApiService::class.java) }
    }
}