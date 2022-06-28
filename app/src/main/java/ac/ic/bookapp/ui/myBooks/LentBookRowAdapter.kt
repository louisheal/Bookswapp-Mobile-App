package ac.ic.bookapp.ui.myBooks

import ac.ic.bookapp.R
import ac.ic.bookapp.data.CoverDatasource
import ac.ic.bookapp.data.CoverSize
import ac.ic.bookapp.model.Loan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LentBookRowAdapter(private val lentBooks: List<Loan>) :
    RecyclerView.Adapter<LentBookRowAdapter.LentBookRowViewHolder>() {

    class LentBookRowViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val bookTitle: TextView = view.findViewById(R.id.lent_book_row_title)
        val bookISBN: TextView = view.findViewById(R.id.lent_books_row_isbn_value)
        val lentTo: TextView = view.findViewById(R.id.lent_book_to_value_row)
        val icon: ImageView = view.findViewById(R.id.lent_book_row_picture)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LentBookRowViewHolder {
        val adapterView =
            LayoutInflater.from(parent.context).inflate(R.layout.row_lent_books, parent, false)
        return LentBookRowViewHolder(adapterView)
    }

    override fun onBindViewHolder(holder: LentBookRowViewHolder, position: Int) {
        val loan = lentBooks[position].request
        holder.bookTitle.text = loan.book.title
        holder.bookISBN.text = loan.book.isbn
        holder.lentTo.text = loan.toUser.name

        val imgURI = CoverDatasource.getBookCover(loan.book, CoverSize.MEDIUM)
        CoverDatasource.loadCover(holder.icon, imgURI)
    }

    override fun getItemCount(): Int = lentBooks.size


}
