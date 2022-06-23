package ac.ic.bookapp.recycleViewAdapters

import ac.ic.bookapp.NotifsFragment
import ac.ic.bookapp.R
import ac.ic.bookapp.data.CoverDatasource
import ac.ic.bookapp.data.CoverSize
import ac.ic.bookapp.data.LoanDatasource
import ac.ic.bookapp.model.LoanRequest
import ac.ic.bookapp.recycleViewAdapters.NotifRowAdapter.NotifViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter

class NotifRowAdapter(
    private val notifsFragment: NotifsFragment,
    private val notifs: List<LoanRequest>
) : Adapter<NotifViewHolder>() {

    class NotifViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        val bookIcon: ImageView = view.findViewById(R.id.notifs_row_book_picture)
        val titleText: TextView = view.findViewById(R.id.notifs_row_title)
        val isbnText: TextView = view.findViewById(R.id.notifs_row_isbn_value)
        val copiesText: TextView = view.findViewById(R.id.notifs_row_copies_value)
        val nameText: TextView = view.findViewById(R.id.notifs_row_name_value)
        val institutionText: TextView = view.findViewById(R.id.notifs_row_institution_value)
        val departmentText: TextView = view.findViewById(R.id.notifs_row_department_value)
        val acceptButton: Button = view.findViewById(R.id.notifs_row_accept_button)
        val denyButton: Button = view.findViewById(R.id.notifs_row_deny_button)
        lateinit var notif: LoanRequest
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifViewHolder {
        val adapterView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.row_notif, parent, false)
        return NotifViewHolder(adapterView)
    }

    override fun onBindViewHolder(holder: NotifViewHolder, position: Int) {
        val notif = notifs[position]
        val book = notif.book
        val requester = notif.fromUser
        holder.notif = notif
        holder.titleText.text = book.title
        holder.isbnText.text = book.isbn
        holder.copiesText.text = notif.copies.toString()
        holder.nameText.text = requester.name
        holder.institutionText.text = "Institution placeholder"
        holder.departmentText.text = "Department placeholder"
        holder.acceptButton.setOnClickListener {
            LoanDatasource.postLoanRequestDecision(notif.id, true)
            notifsFragment.onResume()
            Toast.makeText(notifsFragment.context, "Book loaned to ${requester.name}!", Toast.LENGTH_SHORT).show()
        }
        holder.denyButton.setOnClickListener {
            LoanDatasource.postLoanRequestDecision(notif.id, false)
            notifsFragment.onResume()
        }

        val imgURI = CoverDatasource.getBookCover(book, CoverSize.MEDIUM)
        CoverDatasource.loadCover(holder.bookIcon, imgURI)
    }

    override fun getItemCount(): Int = notifs.size
}