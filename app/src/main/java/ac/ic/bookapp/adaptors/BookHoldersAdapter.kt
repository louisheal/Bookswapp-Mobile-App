package ac.ic.bookapp.adaptors

import ac.ic.bookapp.R
import ac.ic.bookapp.model.Book
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
            val userName: TextView = view.findViewById(R.id.book_holder_user)
            lateinit var user: User
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolderViewHolder {
            val adapterView =
                LayoutInflater.from(parent.context).inflate(R.layout.book_holder_raw, parent, false)
            return BookHolderViewHolder(adapterView)
        }

        override fun onBindViewHolder(holder: BookHolderViewHolder, position: Int) {
            val user = bookHoldersList[position]
            holder.userName.text = user.name
            holder.user = user
        }

        override fun getItemCount(): Int = bookHoldersList.size
    }