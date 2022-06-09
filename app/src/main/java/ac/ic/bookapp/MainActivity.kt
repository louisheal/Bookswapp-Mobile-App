package ac.ic.bookapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {

  private val BORROW: String = "Borrow"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    loadTable(listOf("1", "2"))
    buttonAction()
  }

  fun buttonAction() {
    val button1: Button = findViewById<Button>(R.id.button6)
    button1.setOnClickListener {
      val borrowToast = Toast.makeText(this, "Book Borrowed", Toast.LENGTH_SHORT)
      borrowToast.show()
      deleteRow()
    }
  }

  fun deleteRow() {
    val table = findViewById<TableLayout>(R.id.table)
    val row = findViewById<Button>(R.id.button6).parent as? TableRow

    table.removeView(row)
  }

  fun loadTable(data: List<String>) {
    val table = findViewById<TableLayout>(R.id.table)
    for (book: String in data) {
      val row = createRow(book)
      table.addView(row)
    }
  }

  fun createRow(book: String): TableRow {
    val row = TableRow(this)
    val text = TextView(this)
    text.setText(book)
    row.addView(text)
    val button = Button(this)
    button.setText(BORROW)
    button.setOnClickListener {
      val borrowToast = Toast.makeText(this, "Book Borrowed", Toast.LENGTH_SHORT)
      borrowToast.show()
    }
    row.addView(button)
    return row
  }
}