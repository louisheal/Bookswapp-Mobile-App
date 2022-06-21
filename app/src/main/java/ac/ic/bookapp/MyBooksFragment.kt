package ac.ic.bookapp

import ac.ic.bookapp.data.LoanDatasource
import ac.ic.bookapp.data.UserDatasource
import ac.ic.bookapp.databinding.FragmentMyBooksBinding
import ac.ic.bookapp.filesys.LoginPreferences
import ac.ic.bookapp.model.Loan
import ac.ic.bookapp.model.Ownership
import ac.ic.bookapp.recycleViewAdapters.BookRowAdapter
import ac.ic.bookapp.recycleViewAdapters.BorrowedRowAdapter
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
        ownedList.adapter = activity?.let { BookRowAdapter(getUserBooks()) }
        borrowedList.adapter = activity?.let { BorrowedRowAdapter(getBorrowedBooks()) }
    }

    private fun getUserBooks(): List<Ownership> =
        UserDatasource.getUserOwns(LoginPreferences.getUserLoginId(this.requireActivity()))

    private fun getBorrowedBooks(): List<Loan> =
        LoanDatasource.getUserBorrowedBooks(LoginPreferences.getUserLoginId(this.requireActivity()))
}