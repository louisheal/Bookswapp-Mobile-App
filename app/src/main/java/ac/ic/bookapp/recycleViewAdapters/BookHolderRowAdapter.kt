package ac.ic.bookapp.recycleViewAdapters

import ac.ic.bookapp.MainActivity
import ac.ic.bookapp.R
import ac.ic.bookapp.data.LoanDatasource
import ac.ic.bookapp.filesys.LoginPreferences
import ac.ic.bookapp.model.User
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookHolderRowAdapter(
    private val context: Context,
    private val bookId: Long,
    private val bookOwnersList: List<User>
) : RecyclerView.Adapter<BookHolderRowAdapter.BookHolderViewHolder>() {

    class BookHolderViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.holder_name)
        val department: TextView = view.findViewById(R.id.holder_department)
        val borrowButton: Button = view.findViewById(R.id.borrow_btn)
        lateinit var user: User
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolderViewHolder {
        val adapterView =
            LayoutInflater.from(parent.context).inflate(R.layout.row_book_holder, parent, false)
        return BookHolderViewHolder(adapterView)
    }

    override fun onBindViewHolder(holder: BookHolderViewHolder, position: Int) {
        val owner = bookOwnersList[position]
        holder.name.text = owner.name
        holder.department.text = "department"
        holder.user = owner
        holder.borrowButton.setOnClickListener {
            borrowBook(owner.id)
            startMyBookActivity()
        }
    }

    private fun startMyBookActivity() {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }

    private fun borrowBook(ownerId: Long) {
        LoanDatasource.postUserLoanRequest(
            LoginPreferences.getUserLoginId(context),
            ownerId,
            bookId
        )
    }

    override fun getItemCount(): Int = bookOwnersList.size
}