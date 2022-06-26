package ac.ic.bookapp.recycleViewAdapters

import ac.ic.bookapp.NotifsFragment
import ac.ic.bookapp.R
import ac.ic.bookapp.data.CoverDatasource
import ac.ic.bookapp.data.CoverSize
import ac.ic.bookapp.data.LoanDatasource
import ac.ic.bookapp.filesys.LoginPreferences
import ac.ic.bookapp.messaging.ChannelActivity
import ac.ic.bookapp.messaging.ChannelListActivity
import ac.ic.bookapp.messaging.MessageService
import ac.ic.bookapp.model.LoanRequest
import ac.ic.bookapp.recycleViewAdapters.NotifRowAdapter.NotifViewHolder
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.sendbird.android.*

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

        val context = view.context
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
        val requester = notif.toUser
        holder.notif = notif
        holder.titleText.text = book.title
        holder.isbnText.text = book.isbn
        holder.copiesText.text = notif.copies.toString()
        holder.nameText.text = requester.name
        holder.institutionText.text = "Institution placeholder"
        holder.departmentText.text = "Department placeholder"
        holder.acceptButton.setOnClickListener {
            LoanDatasource.postLoanRequestDecision(notif.id, true)
            notifsFragment.displayRequestConfirmation(book.title, requester.name)
            createMessageChannel(requester.id, holder.context)
        }
        holder.denyButton.setOnClickListener {
            LoanDatasource.postLoanRequestDecision(notif.id, false)
            notifsFragment.onResume()
        }

        val imgURI = CoverDatasource.getBookCover(book, CoverSize.MEDIUM)
        CoverDatasource.loadCover(holder.bookIcon, imgURI)
    }

    private fun createMessageChannel(borrowerId: Long, context: Context) {
        val TAG = "Notifications"
        Log.d(TAG, "creating message channel")
        val lenderId = LoginPreferences.getUserLoginId(context)
//        MessageService.connectToSendBird(lenderId.toString(),
//            LoginPreferences.getUsername(context), context)

        SendBird.connect(lenderId.toString()) { user: User?, e: SendBirdException? ->
            if (user != null) {
                if (e != null) {
                    Log.e(TAG, e.message!!)
                } else {
                    Log.d(TAG, "Connection succeeded")
                    val curUser: User? = SendBird.getCurrentUser()
                    val params = GroupChannelParams()

                    val users = ArrayList<String>()
//        users.add(SendBird.getCurrentUser().userId)
                    users.add(lenderId.toString())
                    users.add(borrowerId.toString())
                    Log.d(TAG, "Users: ${users.toString()}")
                    params.addUserIds(users)

                    GroupChannel.createChannelWithUserIds(users, true) {groupChannel: GroupChannel?, e: SendBirdException? ->
                        if (e != null) {
                            Log.e(TAG, "Channel creation failed")
                            Log.e(TAG, e.message!!)
                        }
                        val url = groupChannel?.url
                        Log.i(TAG, "Url: ${url}")
                        val intent = Intent(context, ChannelActivity::class.java)
                        intent.putExtra(MessageService.EXTRA_CHANNEL_URL, url)
                        context.startActivity(intent)
                    }
                }
            } else {
                Log.e(TAG, "Connection failed: user null")
            }
        }



//        GroupChannel.createChannel(params) { groupChannel, e ->
//            if (e != null) {
//                Log.e("TAG", e.message!!)
//            } else {
//                val intent = Intent(context, ChannelActivity::class.java)
//                intent.putExtra(MessageService.EXTRA_CHANNEL_URL, groupChannel.url)
//                Log.d("Chat Test", groupChannel.url)
//                context.startActivity(intent)
//
//                val params = UserMessageParams()
//                    .setMessage("Test")
//                groupChannel.sendUserMessage(params,
//                    BaseChannel.SendUserMessageHandler { userMessage, e ->
//                        if (e != null) {    // Error.
//                            return@SendUserMessageHandler
//                        }
//                        //adapter.addFirst(userMessage)
//                    })
//            }
//        }
    }

    override fun getItemCount(): Int = notifs.size
}