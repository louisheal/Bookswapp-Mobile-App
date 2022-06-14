package ac.ic.bookapp

import ac.ic.bookapp.adaptors.BorrowBookRowAdapter
import ac.ic.bookapp.data.Datasource
import ac.ic.bookapp.databinding.FragmentMyBooksBinding
import ac.ic.bookapp.databinding.FragmentSearchBinding
import ac.ic.bookapp.model.Book
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        // val books = listOf<Book>(Book("1","2", "Title", "2022-06-14"))
        displayBorrowableBooks(Datasource.getBooks())
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