package ac.ic.bookapp

import ac.ic.bookapp.data.BookDatasource
import ac.ic.bookapp.data.CoverDatasource
import ac.ic.bookapp.data.CoverSize
import ac.ic.bookapp.databinding.FragmentSearchBinding
import ac.ic.bookapp.model.Book
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!

    private lateinit var scrollableList: RecyclerView

    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        scrollableList = binding.searchList
        scrollableList.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(
            this.requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        scrollableList.layoutManager = layoutManager
        scrollableList.addItemDecoration(
            DividerItemDecoration(
                this.requireContext(),
                layoutManager.orientation
            )
        )

        return view
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayBorrowableBooks(BookDatasource.getBooks())
    }

    @Override
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun displayBorrowableBooks(books: List<Book>) {
        val adapter = activity?.let { BorrowBookRowAdapter(it, books) }
        scrollableList.adapter = adapter
    }
}

class BorrowBookRowAdapter(
    private val context: Context,
    private val booksList: List<Book>
) :
    RecyclerView.Adapter<BorrowBookRowAdapter.BorrowBookRowViewHolder>() {

    class BorrowBookRowViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
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
    ): BorrowBookRowViewHolder {
        val adapterView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.search_row, parent, false)
        return BorrowBookRowViewHolder(adapterView)
    }

    override fun onBindViewHolder(holder: BorrowBookRowViewHolder, position: Int) {
        val book = mutableBooksList[position]
        holder.titleText.text = book.title
        holder.book = book
        holder.isbnText.text = book.isbn
        val imgURI = CoverDatasource.getBookCover(book, CoverSize.MEDIUM)
        holder.icon.load(imgURI) {
            placeholder(R.drawable.ic_image)
        }
    }

    override fun getItemCount(): Int = mutableBooksList.size
}