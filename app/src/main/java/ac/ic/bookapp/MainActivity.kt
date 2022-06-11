package ac.ic.bookapp

import ac.ic.bookapp.data.Datasource
import ac.ic.bookapp.databinding.ActivityMainBinding
import ac.ic.bookapp.model.Book
import ac.ic.bookapp.model.JBook
import ac.ic.bookapp.model.User
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val BORROW: String = "Duplicate"
    private var currentID: Int = 5

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val response: List<Book> = Datasource.getBooks()
        displayBooks(response)
    }

    private fun createRow(book: Book): TableRow {
        val row = TableRow(this)

        val icon = ImageView(this)
        icon.id = View.generateViewId()
        icon.setImageResource(R.drawable.ic_book)
        icon.importantForAccessibility = ViewGroup.IMPORTANT_FOR_ACCESSIBILITY_NO
        icon.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.MATCH_PARENT
        )
        row.addView(icon)

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
                val response =
                    Datasource.BookApi.retrofitService.postBook(JBook("5", "new book", "today"))
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
        val table = binding.table
        table.removeAllViews()
        for (book in books) {
            val row = createRow(book)
            table.addView(row)
        }
    }


}

