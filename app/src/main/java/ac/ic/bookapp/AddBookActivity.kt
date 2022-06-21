package ac.ic.bookapp

import ac.ic.bookapp.data.BookDatasource
import ac.ic.bookapp.data.UserDatasource
import ac.ic.bookapp.databinding.ActivityAddBookBinding
import ac.ic.bookapp.filesys.LoginPreferences
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity

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
        binding.scanBookButton.setOnClickListener { startScanner() }
        binding.isbnInfo.setOnClickListener { showHint() }
    }

    private fun startScanner() {
        val intent = Intent(this, ScannerViewActivity::class.java)
        this.startActivity(intent)
        finish()
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

    private fun showHint() {
        if (binding.isbnHintText.text == "") {
            binding.isbnHintText.text = resources.getString(R.string.isbn_help)
        } else {
            binding.isbnHintText.text = ""
        }
    }
}
