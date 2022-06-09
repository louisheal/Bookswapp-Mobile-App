package ac.ic.bookapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val button1: Button = findViewById<Button>(R.id.button6)
    button1.setOnClickListener {
      val borrowToast = Toast.makeText(this, "Book Borrowed", Toast.LENGTH_SHORT)
      borrowToast.show()
    }
  }
}