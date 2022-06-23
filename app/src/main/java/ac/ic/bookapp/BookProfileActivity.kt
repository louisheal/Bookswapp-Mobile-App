package ac.ic.bookapp

import ac.ic.bookapp.data.BookDatasource
import ac.ic.bookapp.databinding.ActivityBookProfileBinding
import ac.ic.bookapp.model.Book
import ac.ic.bookapp.model.User
import ac.ic.bookapp.recycleViewAdapters.BookHolderRowAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class BookProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookProfileBinding
    private lateinit var bookHoldersList: RecyclerView
    private lateinit var book: Book

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookProfileBinding.inflate(layoutInflater)

        setContentView(binding.root)

        book = intent.getSerializableExtra("book") as Book
        binding.bookTitle.text = book.title

        bookHoldersList = findViewById(R.id.book_holders_list)
        bookHoldersList.setHasFixedSize(true)

        displayOwners()
    }

    override fun onStart() {
        super.onStart()
        displayOwners()
    }

    private fun displayOwners() {
        bookHoldersList.adapter = BookHolderRowAdapter(this, book.id, getOwnersList())
    }

    private fun getOwnersList(): List<User> = BookDatasource.getOwners(book.id)
}