package ac.ic.bookapp.adaptors

import ac.ic.bookapp.R
import ac.ic.bookapp.model.Book
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class BorrowBookRowAdapter(private val context: Context,
                           private val booksList: List<Book>):
    RecyclerView.Adapter<BorrowBookRowAdapter.BorrowBookRowViewHolder>() {

    class BorrowBookRowViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val bookTitle: TextView = view.findViewById(R.id.my_books_book_text)
        val borrowButton =  view.findViewById<Button>(R.id.borrow_button)
        val icon = view.findViewById<ImageView>(R.id.search_book_picture)
        lateinit var book: Book
    }

    private val mutableBooksList = booksList.toMutableList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BorrowBookRowViewHolder {
        val adapterView = LayoutInflater.from(parent.context).inflate(R.layout.search_book_row, parent, false)
        return BorrowBookRowViewHolder(adapterView)
    }

    override fun onBindViewHolder(holder: BorrowBookRowViewHolder, position: Int) {
        val book = mutableBooksList[position]
        holder.bookTitle.text = book.title
        holder.book = book

        

        holder.borrowButton.setOnClickListener {
            val toast = Toast.makeText(
                context,
                book.title + " borrowed!", Toast.LENGTH_LONG
            )
            toast.show()

            mutableBooksList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int = mutableBooksList.size
}