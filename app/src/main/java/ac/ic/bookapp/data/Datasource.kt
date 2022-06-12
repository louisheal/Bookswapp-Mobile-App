package ac.ic.bookapp.data

import ac.ic.bookapp.model.Book
import ac.ic.bookapp.model.JBook
import ac.ic.bookapp.model.User
import android.widget.Toast
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

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

    val retrofit = Retrofit.Builder()
        .baseUrl("https://drp19.herokuapp.com/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    interface BookApiService {
        @GET("books")
        suspend fun getBooks(): List<Book>

        @GET("users")
        suspend fun getUsers(): List<User>

        @POST("books")
        suspend fun postBook(@Body book: JBook): JBook
    }

    object BookApi {
        val retrofitService: BookApiService by lazy { retrofit.create(BookApiService::class.java) }
    }
}