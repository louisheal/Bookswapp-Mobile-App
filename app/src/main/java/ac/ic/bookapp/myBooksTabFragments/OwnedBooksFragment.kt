package ac.ic.bookapp.myBooksTabFragments

import ac.ic.bookapp.data.LoginRepository
import ac.ic.bookapp.data.UserDatasource
import ac.ic.bookapp.databinding.FragmentOwnedBooksBinding
import ac.ic.bookapp.model.Ownership
import ac.ic.bookapp.ui.myBooks.OwnedBookRowAdapter
import ac.ic.bookapp.ui.myBooks.add.AddBookActivity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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

        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                displayBooks()
                mainHandler.postDelayed(this, 2000)
            }
        })
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun getUserBooks(): List<Ownership> =
        UserDatasource.getUserOwns(LoginRepository.getUserId())

    private fun startAddBookActivity() {
        val intent = Intent(context, AddBookActivity::class.java)
        context?.startActivity(intent)
    }
}