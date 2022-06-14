package ac.ic.bookapp.adaptors

import ac.ic.bookapp.R
import ac.ic.bookapp.data.Datasource
import ac.ic.bookapp.model.Book
import ac.ic.bookapp.model.JBook
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.runBlocking

class BookRowAdapter
    (
    private val context: Context,
    private val booksList: List<Book>
) : RecyclerView.Adapter<BookRowAdapter.BookRowViewHolder>() {

    class BookRowViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val bookTitle: TextView = view.findViewById(R.id.my_books_book_text)
        lateinit var book: Book
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookRowViewHolder {
        val adapterView =
            LayoutInflater.from(parent.context).inflate(R.layout.my_books_book_raw, parent, false)
        return BookRowViewHolder(adapterView)
    }

    override fun onBindViewHolder(holder: BookRowViewHolder, position: Int) {
        val book = booksList[position]
        holder.bookTitle.text = book.title
        holder.book = book
    }

    override fun getItemCount(): Int = booksList.size
}

