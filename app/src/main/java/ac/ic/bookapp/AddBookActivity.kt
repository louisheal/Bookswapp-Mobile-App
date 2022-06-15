package ac.ic.bookapp

import ac.ic.bookapp.model.BookDatasource
import ac.ic.bookapp.databinding.ActivityAddBookBinding
import ac.ic.bookapp.filesys.LoginPreferences
import ac.ic.bookapp.model.UserDatasource
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import java.lang.NullPointerException

private val ISBN_INFO_QUERY = "https://www.google.com/search?q=what+is+isbn"

open class AddBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        binding.addBookButton.setOnClickListener { addMyBook(binding.isbnEditText.text.toString()) }
        binding.scanBookButton.setOnClickListener {
            val intent = Intent(this, ScannerViewActivity::class.java)
            this.startActivity(intent)
            finish()
        }

        binding.isbnInfo.setOnClickListener {
            val intent = Intent(Intent.ACTION_WEB_SEARCH)
            intent.putExtra(SearchManager.QUERY, ISBN_INFO_QUERY)
            startActivity(intent)
        }
    }

    protected fun addMyBook(isbn: String) {
        val toastText: String = try {
            val book = BookDatasource.postBook(isbn)!!
            val userId = LoginPreferences.getUserLoginId(this)
            UserDatasource.postOwnership(userId, book.id, 1, 1)
            "Book Added!"
        } catch (e: NullPointerException) {
            "Error: ISBN not found"
        }
        Toast.makeText(applicationContext, toastText, LENGTH_SHORT).show()
        finish()
    }
}
