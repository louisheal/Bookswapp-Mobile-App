package ac.ic.bookapp.myBooksTabFragments

import ac.ic.bookapp.data.LoanDatasource
import ac.ic.bookapp.data.LoginRepository
import ac.ic.bookapp.databinding.FragmentLentBooksBinding
import ac.ic.bookapp.model.Loan
import ac.ic.bookapp.ui.myBooks.LentBookRowAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class LentBooksFragment : Fragment() {

    private var _binding: FragmentLentBooksBinding? = null

    private lateinit var lentBooksList: RecyclerView
    private lateinit var emptyLentListText: TextView

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLentBooksBinding.inflate(inflater, container, false)
        lentBooksList = binding.lentBooksList
        emptyLentListText = binding.lentBooksEmptyText
        lentBooksList.setHasFixedSize(true)

        displayBooks()
        return binding.root
    }

    private fun displayBooks() {
        val lentBooks = getLentBooks()
        if (lentBooks.isEmpty()) {
            emptyLentListText.visibility = View.VISIBLE
        } else {
            lentBooksList.adapter = LentBookRowAdapter(lentBooks)
        }
    }

    private fun getLentBooks(): List<Loan> =
        LoanDatasource.getUserLentBooks(LoginRepository.getUserId())

}