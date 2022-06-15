package ac.ic.bookapp

import ac.ic.bookapp.adaptors.BookRowAdapter
import ac.ic.bookapp.model.UserDatasource
import ac.ic.bookapp.databinding.FragmentMyBooksBinding
import ac.ic.bookapp.filesys.LoginPreferences
import ac.ic.bookapp.data.Book
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

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

        binding.addBookFloatingButton.setOnClickListener {
            val intent = Intent(context, AddBookActivity::class.java)
            context?.startActivity(intent)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "Loading books")
        displayBooks(getUserBooks())
    }

    override fun onResume() {
        super.onResume()
        displayBooks(getUserBooks())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun displayBooks(books: List<Book>) {
        val adapter = activity?.let { BookRowAdapter(it, books) }
        scrollableList.adapter = adapter
    }

    private fun getUserBooks(): List<Book> =
        UserDatasource.getUserBooks(
            LoginPreferences.getUserLoginId(this.requireActivity())
        )
}