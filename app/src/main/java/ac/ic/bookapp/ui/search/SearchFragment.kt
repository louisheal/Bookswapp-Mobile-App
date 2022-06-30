package ac.ic.bookapp.ui.search

import ac.ic.bookapp.R
import ac.ic.bookapp.data.BookDatasource
import ac.ic.bookapp.data.LoginRepository
import ac.ic.bookapp.databinding.FragmentSearchBinding
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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

        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                displayBorrowableBooks()
                mainHandler.postDelayed(this, 2000)
            }
        })

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
        val books = BookDatasource.getBooks(LoginRepository.getUserId())
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