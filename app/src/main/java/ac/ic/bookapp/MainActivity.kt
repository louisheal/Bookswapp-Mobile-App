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
    val response : List<MarsPhoto> = getMarsPhotos()
    println(response.size)
    refresh()
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

  fun refresh() {
    val table = findViewById<TableLayout>(R.id.table)
    table.removeAllViews()
    appendTable(listOf("New", "Book", "Added"), table)

  }

  private fun getMarsPhotos(): List<MarsPhoto> {
    var response : List<MarsPhoto> = listOf()
    runBlocking {
      try {
        response = MarsApi.retrofitService.getPhotos()
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
  .baseUrl("https://android-kotlin-fun-mars-server.appspot.com")
  .addConverterFactory(MoshiConverterFactory.create(moshi))
  .build()

interface MarsApiService {
  @GET("photos")
  suspend fun getPhotos() : List<MarsPhoto>
}

object MarsApi {
  val retrofitService: MarsApiService by lazy { retrofit.create(MarsApiService::class.java) }
}

data class MarsPhoto(
  val id: String,
  @Json(name = "img_src") val imgSrcUrl: String
)