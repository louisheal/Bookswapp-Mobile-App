package ac.ic.bookapp.ui.search.borrow

import ac.ic.bookapp.R
import ac.ic.bookapp.data.LoanDatasource
import ac.ic.bookapp.data.LoginRepository
import ac.ic.bookapp.model.User
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class BookHolderRowAdapter(
    private val context: Activity,
    private val bookId: Long,
    private val bookOwnersList: List<User>
) : RecyclerView.Adapter<BookHolderRowAdapter.BookHolderViewHolder>() {

    class BookHolderViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.borrow_row_name_value)
        val institution: TextView = view.findViewById(R.id.borrow_row_institution_value)
        val department: TextView = view.findViewById(R.id.borrow_row_department_value)
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
        holder.institution.text = owner.institution
        holder.department.text = owner.department
        holder.user = owner
        holder.borrowButton.setOnClickListener {
            borrowBook(owner.id)
            requestSentToast()
            context.finish()
        }
    }

    private fun requestSentToast() {
        Toast.makeText(context, "Borrow request was sent!", Toast.LENGTH_LONG).show()
    }

    private fun borrowBook(ownerId: Long) {
        LoanDatasource.postUserLoanRequest(
            LoginRepository.getUserId(),
            ownerId,
            bookId
        )
    }

    override fun getItemCount(): Int = bookOwnersList.size
}