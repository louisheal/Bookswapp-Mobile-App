package ac.ic.bookapp.adaptors

import ac.ic.bookapp.R
import ac.ic.bookapp.model.User
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookHoldersAdapter
    (
    private val context: Context,
    private val bookHoldersList: List<User>
) : RecyclerView.Adapter<BookHoldersAdapter.BookHolderViewHolder>() {

    class BookHolderViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.holder_name)
        val department: TextView = view.findViewById(R.id.holder_department)
        lateinit var user: User
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolderViewHolder {
        val adapterView =
            LayoutInflater.from(parent.context).inflate(R.layout.book_holder_row, parent, false)
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