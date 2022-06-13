package ac.ic.bookapp.adaptors

import ac.ic.bookapp.MyBooksFragment
import ac.ic.bookapp.R
import ac.ic.bookapp.data.Datasource
import ac.ic.bookapp.model.Book
import ac.ic.bookapp.model.JBook
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.runBlocking

class BookRowAdapter
    (
    private val context: Context,
    private val booksList: ArrayList<Book>
) : RecyclerView.Adapter<BookRowAdapter.BookRowViewHolder>() {

    private var currentID = 5

    class BookRowViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val bookTitle: TextView = view.findViewById(R.id.my_books_book_text)
        val bookButton: Button = view.findViewById(R.id.my_books_book_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookRowViewHolder {
        val adapterView =
            LayoutInflater.from(parent.context).inflate(R.layout.book_layout, parent, false)
        return BookRowViewHolder(adapterView)
    }

    override fun onBindViewHolder(holder: BookRowViewHolder, position: Int) {
        val book = booksList[position]
        holder.bookTitle.text = book.title
        holder.bookButton.setOnClickListener {
            book.id = currentID.toString()
            currentID++
            borrowBook(book)
        }
    }

    override fun getItemCount(): Int = booksList.size

    fun borrowBook(book: Book) {
        runBlocking {
            try {
                val response =
                    Datasource.BookApi.retrofitService.postBook(JBook(
                        book.isbn,
                        book.title,
                        book.published
                    ))
                val toast = Toast.makeText(
                    context,
                    response.title, Toast.LENGTH_SHORT
                )
                toast.show()
                book.id = currentID.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

