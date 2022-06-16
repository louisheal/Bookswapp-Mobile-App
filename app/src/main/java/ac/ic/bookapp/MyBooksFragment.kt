package ac.ic.bookapp

import ac.ic.bookapp.adaptors.BookRowAdapter
import ac.ic.bookapp.data.Datasource
import ac.ic.bookapp.databinding.FragmentMyBooksBinding
import ac.ic.bookapp.model.Book
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MyBooksFragment : Fragment() {
    private var _binding: FragmentMyBooksBinding? = null

    private val binding get() = _binding!!

    private lateinit var scrollableList: RecyclerView

    private val TAG = "MyBooksFragment"

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

        binding.addBookFloatingButton.setOnClickListener {
            val intent = Intent(context, AddBookActivity::class.java)
            context?.startActivity(intent)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        displayBooks(Datasource.getBooks())
    }

    override fun onStart() {
        super.onStart()
        displayBooks(Datasource.getBooks())
    }

    override fun onResume() {
        super.onResume()
        displayBooks(Datasource.getBooks())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun displayBooks(books: List<Book>) {
        val adapter = activity?.let { BookRowAdapter(it, books) }
        scrollableList.adapter = adapter
    }
}