package ac.ic.bookapp

import ac.ic.bookapp.data.UserDatasource
import ac.ic.bookapp.databinding.ActivityBookProfileBinding
import ac.ic.bookapp.model.User
import ac.ic.bookapp.recycleViewAdapters.BookHolderRowAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class BookProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookProfileBinding
    private lateinit var bookHoldersList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookProfileBinding.inflate(layoutInflater)
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
        bookHoldersList.adapter = BookHolderRowAdapter(getHoldersList())
    }

    private fun getHoldersList(): List<User> = UserDatasource.getUsers()
}