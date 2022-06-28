package ac.ic.bookapp.ui.myBooks

import ac.ic.bookapp.ui.myBooks.add.AddBookActivity
import ac.ic.bookapp.data.LoanDatasource
import ac.ic.bookapp.data.UserDatasource
import ac.ic.bookapp.databinding.FragmentMyBooksBinding
import ac.ic.bookapp.filesys.LoginPreferences
import ac.ic.bookapp.model.Loan
import ac.ic.bookapp.model.Ownership
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "MyBooksFragment"

class MyBooksFragment : Fragment() {
    private var _binding: FragmentMyBooksBinding? = null

    private val binding get() = _binding!!

    private lateinit var ownedList: RecyclerView
    private lateinit var borrowedList: RecyclerView
    private lateinit var lentList: RecyclerView

    private lateinit var emptyOwnedListText: TextView
    private lateinit var emptyBorrowedListText: TextView
    private lateinit var emptyLentListText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "View creation")
        _binding = FragmentMyBooksBinding.inflate(inflater, container, false)
        val view = binding.root

        ownedList = binding.myBooksList
        borrowedList = binding.borrowedBooksList
        lentList = binding.lentList
        emptyOwnedListText = binding.ownedBooksEmptyText
        emptyBorrowedListText = binding.borrowedBooksEmptyText
        emptyLentListText = binding.lentBooksEmptyText

        ownedList.setHasFixedSize(true)
        borrowedList.setHasFixedSize(true)
        lentList.setHasFixedSize(true)

        binding.addBookFloatingButton.setOnClickListener {
            startAddBookActivity()
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

    private fun startAddBookActivity() {
        val intent = Intent(context, AddBookActivity::class.java)
        context?.startActivity(intent)
    }

    private fun displayBooks() {
        val ownedBooks = getUserBooks()
        val borrowedBooks = getBorrowedBooks()
        val lentBooks = getLentBooks()
        if (ownedBooks.isEmpty()) {
            emptyOwnedListText.visibility = View.VISIBLE
        } else {
            ownedList.adapter = OwnedBookRowAdapter(ownedBooks)
        }
        if (borrowedBooks.isEmpty()) {
            emptyBorrowedListText.visibility = View.VISIBLE
        } else {
            borrowedList.adapter = BorrowedBookRowAdapter(borrowedBooks)
        }
        if (lentBooks.isEmpty()) {
            emptyLentListText.visibility = View.VISIBLE
        } else {
            lentList.adapter = LentBookRowAdapter(lentBooks)
        }
    }

    private fun getLentBooks(): List<Loan> =
        LoanDatasource.getUserLentBooks(LoginPreferences.getUserLoginId(this.requireActivity()))

    private fun getUserBooks(): List<Ownership> =
        UserDatasource.getUserOwns(LoginPreferences.getUserLoginId(this.requireActivity()))

    private fun getBorrowedBooks(): List<Loan> =
        LoanDatasource.getUserBorrowedBooks(LoginPreferences.getUserLoginId(this.requireActivity()))
}