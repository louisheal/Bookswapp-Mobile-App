package ac.ic.bookapp.recycleViewAdapters

import ac.ic.bookapp.R
import ac.ic.bookapp.data.CoverDatasource
import ac.ic.bookapp.data.CoverSize
import ac.ic.bookapp.model.Book
import ac.ic.bookapp.model.Loan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BorrowedBookRowAdapter(
    private val borrowedList: List<Loan>
) : RecyclerView.Adapter<BorrowedBookRowAdapter.BorrowedBookRowViewHolder>() {

    class BorrowedBookRowViewHolder(
        private val view: View
    ) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.borrowed_books_row_title)
        val isbnText: TextView = view.findViewById(R.id.borrowed_books_row_isbn_value)
        val borrowedFromText: TextView = view.findViewById(R.id.borrowed_books_row_from_value)
        val borrowedCopiesText: TextView = view.findViewById(R.id.borrowed_books_row_copies_value)

        //        val returnButton: Button = view.findViewById(R.id.borrowed_books_row_return_button)
        val icon: ImageView = view.findViewById(R.id.borrowed_books_book_picture)
        lateinit var book: Book
        lateinit var loan: Loan
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BorrowedBookRowViewHolder {
        val adapterView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_borrowed_books, parent, false)
        return BorrowedBookRowViewHolder(adapterView)
    }

    override fun onBindViewHolder(holder: BorrowedBookRowViewHolder, position: Int) {
        val loan = borrowedList[position]
        val book = loan.request.book
        holder.loan = loan
        holder.book = book
        holder.titleText.text = book.title
        holder.isbnText.text = book.isbn
        holder.borrowedFromText.text = loan.request.fromUser.name
        holder.borrowedCopiesText.text = loan.request.copies.toString()

        val imgURI = CoverDatasource.getBookCover(book, CoverSize.MEDIUM)

        CoverDatasource.loadCover(holder.icon, imgURI)
    }

    override fun getItemCount(): Int = borrowedList.size
}