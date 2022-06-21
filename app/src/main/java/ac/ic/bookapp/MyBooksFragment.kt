package ac.ic.bookapp

import ac.ic.bookapp.BookRowAdapter.BookRowViewHolder
import ac.ic.bookapp.BorrowedRowAdapter.BorrowedRowViewHolder
import ac.ic.bookapp.data.CoverDatasource
import ac.ic.bookapp.data.CoverSize
import ac.ic.bookapp.data.LoanDatasource
import ac.ic.bookapp.data.UserDatasource
import ac.ic.bookapp.databinding.FragmentMyBooksBinding
import ac.ic.bookapp.filesys.LoginPreferences
import ac.ic.bookapp.model.Book
import ac.ic.bookapp.model.Loan
import ac.ic.bookapp.model.Ownership
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

private const val TAG = "MyBooksFragment"

class MyBooksFragment : Fragment() {
    private var _binding: FragmentMyBooksBinding? = null

    private val binding get() = _binding!!

    private lateinit var ownedList: RecyclerView
    private lateinit var borrowedList: RecyclerView

//    private val userId = LoginPreferences.getUserLoginId(this.requireActivity())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "View creation")
        _binding = FragmentMyBooksBinding.inflate(inflater, container, false)
        val view = binding.root

        ownedList = view.findViewById(R.id.my_books_list)
        borrowedList = view.findViewById(R.id.borrowed_books_list)
        ownedList.setHasFixedSize(true)
        borrowedList.setHasFixedSize(true)

        binding.addBookFloatingButton.setOnClickListener {
            val intent = Intent(context, AddBookActivity::class.java)
            context?.startActivity(intent)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "Loading books")
        displayBooks()
    }

    override fun onStart() {
        super.onStart()
        displayBooks()
    }

    override fun onResume() {
        super.onResume()
        displayBooks()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun displayBooks() {
        ownedList.adapter = activity?.let { BookRowAdapter(it, getUserBooks()) }
        borrowedList.adapter = activity?.let { BorrowedRowAdapter(it, getBorrowedBooks()) }
    }

    private fun getUserBooks(): List<Ownership> =
        UserDatasource.getUserOwns(LoginPreferences.getUserLoginId(this.requireActivity()))

    private fun getBorrowedBooks(): List<Loan> =
        LoanDatasource.getUserBorrowedBooks(LoginPreferences.getUserLoginId(this.requireActivity()))
}

class BookRowAdapter(
    private val context: Context,
    private val ownsList: List<Ownership>
) : Adapter<BookRowViewHolder>() {

    class BookRowViewHolder(
        private val view: View
    ) : ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.my_books_row_title)
        val isbnText: TextView = view.findViewById(R.id.my_books_row_isbn_value)
        val totalCopiesText: TextView = view.findViewById(R.id.my_books_row_total_copies_value)
        val currentCopiesText: TextView = view.findViewById(R.id.my_books_row_current_copies_value)
        val icon: ImageView = view.findViewById(R.id.borrowed_books_book_picture)
        lateinit var book: Book
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookRowViewHolder {
        val adapterView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_my_books, parent, false)
        return BookRowViewHolder(adapterView)
    }

    override fun onBindViewHolder(holder: BookRowViewHolder, position: Int) {
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

class BorrowedRowAdapter(
    private val context: Context,
    private val borrowedList: List<Loan>
) : Adapter<BorrowedRowViewHolder>() {

    class BorrowedRowViewHolder(
        private val view: View
    ) : ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.borrowed_books_row_title)
        val isbnText: TextView = view.findViewById(R.id.borrowed_books_row_isbn_value)
        val borrowedFromText: TextView = view.findViewById(R.id.borrowed_books_row_from_value)
        val borrowedCopiesText: TextView = view.findViewById(R.id.borrowed_books_row_copies_value)

        //        val returnButton: Button = view.findViewById(R.id.borrowed_books_row_return_button)
        val icon: ImageView = view.findViewById(R.id.borrowed_books_book_picture)
        lateinit var book: Book
        lateinit var loan: Loan
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BorrowedRowViewHolder {
        val adapterView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_borrowed_books, parent, false)
        return BorrowedRowViewHolder(adapterView)
    }

    override fun onBindViewHolder(holder: BorrowedRowViewHolder, position: Int) {
        val loan = borrowedList[position]
        val book = loan.book
        holder.loan = loan
        holder.book = book
        holder.titleText.text = book.title
        holder.isbnText.text = book.isbn
        holder.borrowedFromText.text = loan.fromUser.name
        holder.borrowedCopiesText.text = loan.copies.toString()

        val imgURI = CoverDatasource.getBookCover(book, CoverSize.MEDIUM)

        CoverDatasource.loadCover(holder.icon, imgURI)
    }

    override fun getItemCount(): Int = borrowedList.size
}