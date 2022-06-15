package ac.ic.bookapp

import ac.ic.bookapp.MainActivity
import ac.ic.bookapp.data.BookDatasource
import ac.ic.bookapp.databinding.ActivityAddBookBinding
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AddBookActivity : AppCompatActivity() {

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
        }
    }

    private fun addMyBook(isbn: String) {
        BookDatasource.postBook(isbn)
        // TODO: starts MainActivity, might not be MyBooks when there are more
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
