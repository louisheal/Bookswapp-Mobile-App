package ac.ic.bookapp.ui.myBooks

import ac.ic.bookapp.R
import ac.ic.bookapp.data.CoverDatasource
import ac.ic.bookapp.data.CoverSize
import ac.ic.bookapp.model.Book
import ac.ic.bookapp.model.Ownership
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OwnedBookRowAdapter(
    private val ownsList: List<Ownership>
) : RecyclerView.Adapter<OwnedBookRowAdapter.OwnedBookRowViewHolder>() {

    class OwnedBookRowViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.my_books_row_title)
        val isbnText: TextView = view.findViewById(R.id.my_books_row_isbn_value)
        val totalCopiesText: TextView = view.findViewById(R.id.my_books_row_total_copies_value)
        val currentCopiesText: TextView = view.findViewById(R.id.my_books_row_current_copies_value)
        val icon: ImageView = view.findViewById(R.id.borrowed_books_book_picture)
        lateinit var book: Book
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OwnedBookRowViewHolder {
        val adapterView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_my_books, parent, false)
        return OwnedBookRowViewHolder(adapterView)
    }

    override fun onBindViewHolder(holder: OwnedBookRowViewHolder, position: Int) {
        val owns = ownsList[position]
        val book = owns.book
        holder.book = book
        holder.titleText.text = book.title
        holder.isbnText.text = book.isbn
        holder.totalCopiesText.text = owns.totalCopies.toString()
        holder.currentCopiesText.text = owns.currentCopies.toString()

        val imgURI = CoverDatasource.getBookCover(book, CoverSize.MEDIUM)
        CoverDatasource.loadCover(holder.icon, imgURI)
    }

    override fun getItemCount(): Int = ownsList.size
}