package ac.ic.bookapp.ui.search.borrow

import ac.ic.bookapp.R
import ac.ic.bookapp.data.BookDatasource
import ac.ic.bookapp.data.CoverDatasource
import ac.ic.bookapp.data.CoverSize
import ac.ic.bookapp.databinding.ActivityBookProfileBinding
import ac.ic.bookapp.model.Book
import ac.ic.bookapp.model.User
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class BookProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookProfileBinding
    private lateinit var bookHoldersList: RecyclerView
    private lateinit var book: Book

    private val ownersList: List<User> by lazy {
        BookDatasource.getOwners(book.id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        book = intent.getSerializableExtra("book") as Book
        binding.bookTitle.text = book.title
        binding.bookPageIsbnValue.text = book.isbn
        binding.bookPagePublishedValue.text = book.published
        binding.bookPageOwnersValue.text = ownersList.size.toString()

        bookHoldersList = findViewById(R.id.book_holders_list)
        bookHoldersList.setHasFixedSize(true)

        val imgURI = CoverDatasource.getBookCover(book, CoverSize.MEDIUM)
        CoverDatasource.loadCover(binding.bookPageImage, imgURI)

        displayOwners()
    }

    override fun onStart() {
        super.onStart()
        displayOwners()
    }

    private fun displayOwners() {
        bookHoldersList.adapter = BookHolderRowAdapter(this, book.id, ownersList)
    }
}