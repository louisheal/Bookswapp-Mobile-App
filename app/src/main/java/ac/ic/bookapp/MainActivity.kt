package ac.ic.bookapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

  private val BORROW: String = "Borrow"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val response : List<Book> = getBooks()
    displayBooks(response)
  }

  fun appendTable(data: List<String>, table: TableLayout) {
    for (book: String in data) {
      val row = createRow(book)
      table.addView(row)
    }
  }

  fun createRow(book: String): TableRow {
    val row = TableRow(this)
    val text = TextView(this)
    text.setText(book)
    row.addView(text)
    val button = Button(this)
    button.setText(BORROW)
    button.setOnClickListener {
      val borrowToast = Toast.makeText(this, "Book Borrowed", Toast.LENGTH_SHORT)
      borrowToast.show()
    }
    row.addView(button)
    return row
  }

  fun refresh(debugString : String) {
    val table = findViewById<TableLayout>(R.id.table)
    table.removeAllViews()
    appendTable(listOf("New", "Book", debugString), table)
  }

  fun displayBooks(books : List<Book>) {
    val table = findViewById<TableLayout>(R.id.table)
    val bookNames : MutableList<String> = mutableListOf()
    table.removeAllViews()
    for (book in books) {
      bookNames.add(book.title)
    }
    appendTable(bookNames, table)
  }

  private fun getBooks(): List<Book> {
    var response : List<Book> = listOf()
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
    var response : List<User> = listOf()
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
  suspend fun getBooks() : List<Book>

  @GET("users")
  suspend fun getUsers() : List<User>
}

object BookApi {
  val retrofitService: BookApiService by lazy { retrofit.create(BookApiService::class.java) }
}

data class Book(
  val id: String,
  val isbn: String,
  val title: String,
  val published: String
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