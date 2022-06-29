package ac.ic.bookapp.ui.search

import ac.ic.bookapp.R
import ac.ic.bookapp.data.BookDatasource
import ac.ic.bookapp.data.CoverDatasource
import ac.ic.bookapp.data.CoverSize
import ac.ic.bookapp.data.LoginRepository
import ac.ic.bookapp.model.Book
import ac.ic.bookapp.ui.search.borrow.BookProfileActivity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchBookRowAdapter(
    private val context: Context,
    private val booksList: List<Book>
) : RecyclerView.Adapter<SearchBookRowAdapter.SearchBookRowViewHolder>() {

    class SearchBookRowViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.search_row_title)
        val isbnText: TextView = view.findViewById(R.id.search_row_isbn_value)
        val ownersText: TextView = view.findViewById(R.id.search_row_owners_value)
        val icon: ImageView = view.findViewById(R.id.search_row_book_picture)
        lateinit var book: Book
    }

    private val mutableBooksList = booksList.toMutableList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchBookRowViewHolder {
        val adapterView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_search, parent, false)
        return SearchBookRowViewHolder(adapterView)
    }

    override fun onBindViewHolder(holder: SearchBookRowViewHolder, position: Int) {
        val book = mutableBooksList[position]
        holder.titleText.text = book.title
        holder.book = book
        holder.isbnText.text = book.isbn
        holder.ownersText.text = BookDatasource.getOwnersCount(
            book.id,
            LoginRepository.getUserId()
        ).toString()
        val imgURI = CoverDatasource.getBookCover(book, CoverSize.MEDIUM)
        CoverDatasource.loadCover(holder.icon, imgURI)
        holder.itemView.setOnClickListener { startBookProfileActivity(book) }
    }

    override fun getItemCount(): Int = mutableBooksList.size

    private fun startBookProfileActivity(book: Book) {
        val intent = Intent(context, BookProfileActivity::class.java)
        intent.putExtra("book", book)
        context.startActivity(intent)
    }
}