package ac.ic.bookapp

import ac.ic.bookapp.data.Datasource
import ac.ic.bookapp.databinding.FragmentMyBooksBinding
import ac.ic.bookapp.model.Book
import ac.ic.bookapp.model.JBook
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.coroutines.runBlocking

class MyBooksFragment : Fragment() {
    private var _binding: FragmentMyBooksBinding? = null

    private val binding get() = _binding!!

    private val BORROW: String = "Duplicate"
    private var currentID: Int = 5

    private val TAG = "MyBooksFragment"

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "View creation")
        _binding = FragmentMyBooksBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "Loading books")
        val response: List<Book> = Datasource.getBooks()
        displayBooks(response)
    }

    @Override
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun displayBooks(books: List<Book>) {
        val table = binding.table
        table.removeAllViews()
        for (book in books) {
            val row = createRow(book)
            table.addView(row)
        }
    }

    private fun createRow(book: Book): TableRow {
        val row = TableRow(context)

        val icon = ImageView(context)
        icon.id = View.generateViewId()
        icon.setImageResource(R.drawable.ic_book)
        icon.importantForAccessibility = ViewGroup.IMPORTANT_FOR_ACCESSIBILITY_NO
        icon.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.MATCH_PARENT
        )
        row.addView(icon)

        val text = TextView(context)
        text.setText(book.title)
        row.addView(text)

        val button = Button(context)
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
                    context,
                    response.code().toString(), Toast.LENGTH_SHORT
                )
                toast.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}