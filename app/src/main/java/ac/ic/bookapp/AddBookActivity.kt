package ac.ic.bookapp

import ac.ic.bookapp.data.Datasource
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ac.ic.bookapp.databinding.ActivityAddBookBinding
import ac.ic.bookapp.model.JBook
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.transaction
import kotlinx.coroutines.runBlocking
import ac.ic.bookapp.MainActivity as MainActivity

class AddBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBookBinding
    private val TAG = "AddBookActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        binding.addBookButton.setOnClickListener { addMyBook() }
    }

    private fun addMyBook() {

        val book = formBook()
        logBookFormation(book)

        runBlocking {
            try {
                val response =
                    Datasource.BookApi.retrofitService.postBook(book)
                val toast = Toast.makeText(
                    this@AddBookActivity,
                    response.title, Toast.LENGTH_SHORT
                )
                toast.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        // TODO: starts MainActivity, might not be MyBooks when there are more
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    // TODO: add checks and catch possible errors
    private fun formBook(): JBook =
        JBook(
            binding.isbnEditText.text.toString(),
            binding.titleEditText.text.toString(),
            binding.publishedEditText.text.toString()
        )

    private fun logBookFormation(book: JBook) {
        Log.i(TAG, book.title)
        Log.i(TAG, book.isbn)
        Log.i(TAG, book.published)
    }
}