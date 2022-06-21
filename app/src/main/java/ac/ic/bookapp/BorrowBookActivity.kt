package ac.ic.bookapp

import ac.ic.bookapp.data.UserDatasource
import ac.ic.bookapp.databinding.ActivityBorrowBookBinding
import ac.ic.bookapp.model.User
import ac.ic.bookapp.recycleViewAdapters.BookHoldersAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class BorrowBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBorrowBookBinding
    private lateinit var bookHoldersList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBorrowBookBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        bookHoldersList = view.findViewById(R.id.book_holders_list)
        bookHoldersList.setHasFixedSize(true)

        displayHolders()
    }

    override fun onStart() {
        super.onStart()
        displayHolders()
    }

    private fun displayHolders() {
        bookHoldersList.adapter = BookHoldersAdapter(getHoldersList())
    }

    private fun getHoldersList(): List<User> = UserDatasource.getUsers()
}