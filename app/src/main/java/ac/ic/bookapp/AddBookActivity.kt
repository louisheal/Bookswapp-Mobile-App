package ac.ic.bookapp

import ac.ic.bookapp.MainActivity
import ac.ic.bookapp.data.Datasource
import ac.ic.bookapp.databinding.ActivityAddBookBinding
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity


class AddBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBookBinding

    private val ISBN_INFO_QUERY = "https://www.google.com/search?q=what+is+isbn"

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

        binding.isbnInfo.setOnClickListener { showHint() }
    }

    private fun addMyBook(isbn: String) {
        try {
            Datasource.postBook(isbn)
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "Error", LENGTH_SHORT).show()
            finish()
        }
        Toast.makeText(applicationContext, "Book Added!", LENGTH_SHORT).show()
        finish()
    }

    private fun showHint() {
        if (binding.isbnHintText.text == "") {
            binding.isbnHintText.text = resources.getString(R.string.isbn_help);
        } else {
            binding.isbnHintText.text = ""
        }
    }
}
