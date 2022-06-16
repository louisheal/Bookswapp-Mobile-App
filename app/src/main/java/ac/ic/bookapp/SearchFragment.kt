package ac.ic.bookapp

import ac.ic.bookapp.data.BookDatasource
import ac.ic.bookapp.databinding.FragmentSearchBinding
import ac.ic.bookapp.model.Book
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

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
        val bookTitle: TextView = view.findViewById(R.id.my_books_book_text)
        val borrowButton = view.findViewById<Button>(R.id.borrow_button)
        lateinit var book: Book
    }

    private val mutableBooksList = booksList.toMutableList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BorrowBookRowViewHolder {
        val adapterView =
            LayoutInflater.from(parent.context).inflate(R.layout.search_book_row, parent, false)
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