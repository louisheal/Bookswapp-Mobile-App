package ac.ic.bookapp

import ac.ic.bookapp.recycleViewAdapters.SearchBookRowAdapter
import ac.ic.bookapp.data.BookDatasource
import ac.ic.bookapp.databinding.FragmentSearchBinding
import ac.ic.bookapp.model.Book
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        scrollableList = binding.searchList
        scrollableList.setHasFixedSize(true)

        return binding.root
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
        scrollableList.adapter =
            activity?.let {
                SearchBookRowAdapter(it, books)
            }
    }
}