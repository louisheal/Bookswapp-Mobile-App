package ac.ic.bookapp

import ac.ic.bookapp.data.UserDatasource
import ac.ic.bookapp.databinding.ActivityBorrowBookBinding
import ac.ic.bookapp.model.User
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class BorrowBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBorrowBookBinding
    private lateinit var bookHoldersList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBorrowBookBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        bookHoldersList = view.findViewById(R.id.book_holders_list)
        bookHoldersList.setHasFixedSize(true)

        displayHolders()
    }

    override fun onStart() {
        super.onStart()
        displayHolders()
    }

    private fun displayHolders() {
        bookHoldersList.adapter = BookHoldersAdapter(getHoldersList())
    }

    private fun getHoldersList(): List<User> = UserDatasource.getUsers()
}


class BookHoldersAdapter
    (
    private val bookHoldersList: List<User>
) : RecyclerView.Adapter<BookHoldersAdapter.BookHolderViewHolder>() {

    class BookHolderViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.holder_name)
        val department: TextView = view.findViewById(R.id.holder_department)
        lateinit var user: User
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolderViewHolder {
        val adapterView =
            LayoutInflater.from(parent.context).inflate(R.layout.row_book_holder, parent, false)
        return BookHolderViewHolder(adapterView)
    }

    override fun onBindViewHolder(holder: BookHolderViewHolder, position: Int) {
        val user = bookHoldersList[position]
        holder.name.text = user.name
        holder.department.text = "department"
        holder.user = user
    }

    override fun getItemCount(): Int = bookHoldersList.size
}