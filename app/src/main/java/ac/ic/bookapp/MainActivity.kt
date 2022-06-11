package ac.ic.bookapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.*
import retrofit2.Response
import retrofit2.http.*

class MainActivity : AppCompatActivity() {

    private val BORROW: String = "Duplicate"
    private var currentID: Int = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val response: List<Book> = getBooks()
        displayBooks(response)
    }

    private fun createRow(book: Book): TableRow {
        val row = TableRow(this)
        val text = TextView(this)
        text.setText(book.title)
        row.addView(text)
        val button = Button(this)
        button.setText(BORROW)
        button.setOnClickListener {
//      val borrowToast = Toast.makeText(this, "Book Duplicated", Toast.LENGTH_SHORT)
//      borrowToast.show()
            book.id = currentID.toString()
            currentID++
            borrowBook(book)
        }
        row.addView(button)
        return row
    }

    private fun borrowBook(book: Book) {
//    val table = findViewById<TableLayout>(R.id.table)
//    val row = createRow(book)
//    table.addView(row)
        runBlocking {
            try {
                val response = BookApi.retrofitService.postBook(JBook("5", "new book", "today"))
                val toast = Toast.makeText(
                    this@MainActivity,
                    response.code().toString(), Toast.LENGTH_SHORT
                )
                toast.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun displayBooks(books: List<Book>) {
        val table = findViewById<TableLayout>(R.id.table)
        table.removeAllViews()
        for (book in books) {
            val row = createRow(book)
            table.addView(row)
        }
    }

    private fun getBooks(): List<Book> {
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

    private fun getUsers(): List<User> {
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
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl("http://146.169.43.115:8080/")
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface BookApiService {
    @GET("books")
    suspend fun getBooks(): List<Book>

    @GET("users")
    suspend fun getUsers(): List<User>

    @Headers("Content-Type: application/json")
    @POST("books")
    suspend fun postBook(@Body book: JBook): Response<Unit>
}

object BookApi {
    val retrofitService: BookApiService by lazy { retrofit.create(BookApiService::class.java) }
}

data class Book(
    @field:Json(name = "id") var id: String,
    @field:Json(name = "isbn") val isbn: String,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "published") val published: String
)

@JsonClass(generateAdapter = true)
data class JBook(
    @field:Json(name = "isbn") val isbn: String,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "published") val published: String
)

data class User(
    val id: String,
    val username: String,
    val passwdHash: String,
    val name: String,
    val email: String,
    val phone: String,
    val joinDate: String
)