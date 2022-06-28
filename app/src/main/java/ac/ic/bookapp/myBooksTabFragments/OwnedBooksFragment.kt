package ac.ic.bookapp.myBooksTabFragments

import ac.ic.bookapp.AddBookActivity
import ac.ic.bookapp.data.UserDatasource
import ac.ic.bookapp.databinding.FragmentOwnedBooksBinding
import ac.ic.bookapp.filesys.LoginPreferences
import ac.ic.bookapp.model.Ownership
import ac.ic.bookapp.recycleViewAdapters.OwnedBookRowAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView


class OwnedBooksFragment : Fragment() {

    private var _binding: FragmentOwnedBooksBinding? = null

    private lateinit var ownedList: RecyclerView
    private lateinit var emptyOwnedListText: TextView

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOwnedBooksBinding.inflate(inflater, container, false)
        ownedList = binding.ownedList
        emptyOwnedListText = binding.ownedBooksEmptyText

        ownedList.setHasFixedSize(true)
        binding.addBookFloatingButton.setOnClickListener {
            startAddBookActivity()
        }

        with(binding.ownedBooksRefresh) {
            setOnRefreshListener {
                displayBooks()
                isRefreshing = false
            }
        }

        displayBooks()
        return binding.root
    }

    private fun displayBooks() {
        val ownedBooks = getUserBooks()
        if (ownedBooks.isEmpty()) {
            emptyOwnedListText.visibility = View.VISIBLE
        } else {
            ownedList.adapter = OwnedBookRowAdapter(ownedBooks)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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


    private fun getUserBooks(): List<Ownership> =
        UserDatasource.getUserOwns(LoginPreferences.getUserLoginId(this.requireActivity()))

    private fun startAddBookActivity() {
        val intent = Intent(context, AddBookActivity::class.java)
        context?.startActivity(intent)
    }
}