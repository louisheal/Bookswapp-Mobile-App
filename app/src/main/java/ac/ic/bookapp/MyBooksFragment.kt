package ac.ic.bookapp

import ac.ic.bookapp.BookRowAdapter.BookRowViewHolder
import ac.ic.bookapp.data.UserDatasource
import ac.ic.bookapp.databinding.FragmentMyBooksBinding
import ac.ic.bookapp.filesys.LoginPreferences
import ac.ic.bookapp.model.Book
import ac.ic.bookapp.model.Ownership
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

private const val TAG = "MyBooksFragment"

class MyBooksFragment : Fragment() {
    private var _binding: FragmentMyBooksBinding? = null

    private val binding get() = _binding!!

    private lateinit var scrollableList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "View creation")
        _binding = FragmentMyBooksBinding.inflate(inflater, container, false)
        val view = binding.root
        scrollableList = view.findViewById(R.id.my_books_list)
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
        val adapter = activity?.let { BookRowAdapter(it, getUserBooks()) }
        scrollableList.adapter = adapter
    }

    private fun getUserBooks(): List<Ownership> =
        UserDatasource.getUserOwns(
            LoginPreferences.getUserLoginId(this.requireActivity())
        )
}

class BookRowAdapter(
    private val context: Context,
    private val ownsList: List<Ownership>
) : RecyclerView.Adapter<BookRowViewHolder>() {

    class BookRowViewHolder(
        private val view: View
    ) : ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.my_books_row_title)
        val isbnText: TextView = view.findViewById(R.id.my_books_row_isbn_value)
        val totalCopiesText: TextView = view.findViewById(R.id.my_books_row_total_copies_value)
        val currentCopiesText: TextView = view.findViewById(R.id.my_books_row_current_copies_value)
        lateinit var book: Book
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookRowViewHolder {
        val adapterView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.my_books_row, parent, false)
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
    }

    override fun getItemCount(): Int = ownsList.size
}