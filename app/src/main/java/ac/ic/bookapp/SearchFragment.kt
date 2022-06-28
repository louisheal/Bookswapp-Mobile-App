package ac.ic.bookapp

import ac.ic.bookapp.data.BookDatasource
import ac.ic.bookapp.databinding.FragmentSearchBinding
import ac.ic.bookapp.filesys.LoginPreferences
import ac.ic.bookapp.recycleViewAdapters.SearchBookRowAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!

    private lateinit var scrollableList: RecyclerView

    private lateinit var emptySearchText: TextView

    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        scrollableList = binding.searchList
        scrollableList.setHasFixedSize(true)

        emptySearchText = binding.searchBooksEmptyText

        with(binding.searchBooksRefresh) {
            setOnRefreshListener {
                displayBorrowableBooks()
                isRefreshing = false
            }
        }

        return binding.root
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayBorrowableBooks()
    }

    @Override
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun displayBorrowableBooks() {
        val books = BookDatasource.getBooks(LoginPreferences.getUserLoginId(this.requireActivity()))
        if (books.isEmpty()) {
            emptySearchText.visibility = View.VISIBLE
        } else {
            scrollableList.adapter =
                activity?.let {
                    SearchBookRowAdapter(it, books)
                }
        }
    }
}