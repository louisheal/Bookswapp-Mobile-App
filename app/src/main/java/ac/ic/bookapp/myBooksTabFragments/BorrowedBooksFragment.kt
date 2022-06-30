package ac.ic.bookapp.myBooksTabFragments

import ac.ic.bookapp.data.LoanDatasource
import ac.ic.bookapp.data.LoginRepository
import ac.ic.bookapp.databinding.FragmentBorrowedBooksBinding
import ac.ic.bookapp.model.Loan
import ac.ic.bookapp.ui.myBooks.BorrowedBookRowAdapter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class BorrowedBooksFragment : Fragment() {

    private var _binding: FragmentBorrowedBooksBinding? = null

    private lateinit var borrowedBooksList: RecyclerView
    private lateinit var emptyBorrowedListText: TextView

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBorrowedBooksBinding.inflate(inflater, container, false)
        borrowedBooksList = binding.borrowedBooksList
        emptyBorrowedListText = binding.borrowedBooksEmptyText
        borrowedBooksList.setHasFixedSize(true)

        with(binding.borrowedBooksRefresh) {
            setOnRefreshListener {
                displayBooks()
                isRefreshing = false
            }
        }

        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                displayBooks()
                mainHandler.postDelayed(this, 2000)
            }
        })
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun displayBooks() {
        val borrowedBooks = getBorrowedBooks()
        if (borrowedBooks.isEmpty()) {
            emptyBorrowedListText.visibility = View.VISIBLE
        } else {
            borrowedBooksList.adapter = BorrowedBookRowAdapter(getBorrowedBooks())
        }
    }

    private fun getBorrowedBooks(): List<Loan> =
        LoanDatasource.getUserBorrowedBooks(LoginRepository.getUserId())
}
