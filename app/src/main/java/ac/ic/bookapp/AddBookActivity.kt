package ac.ic.bookapp

import ac.ic.bookapp.data.Datasource
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import ac.ic.bookapp.databinding.ActivityAddBookBinding
import ac.ic.bookapp.model.Book
import ac.ic.bookapp.model.JBook
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.runBlocking

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

        binding.addBookButton.setOnClickListener {
            postBook()
        }
    }

    private fun postBook() {
        val title = binding.titleEditText
        val isbn = binding.isbnEditText
        val published = binding.publishedEditText

        Log.i(TAG, title.text.toString())
        Log.i(TAG, isbn.text.toString())
        Log.i(TAG, published.text.toString())

        val book = JBook(isbn.text.toString(),
            title.text.toString(),
            published.text.toString())

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

        finish()
    }
}