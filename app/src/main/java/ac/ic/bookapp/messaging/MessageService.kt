package ac.ic.bookapp.messaging

import ac.ic.bookapp.data.LoginRepository
import ac.ic.bookapp.model.Book
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.sendbird.android.*

object MessageService {

    val EXTRA_CHANNEL_URL: String = "EXTRA_CHANNEL_URL"
    private val UNIQUE_HANDLER_ID = "1on1_Handler"

    fun connectToSendBird(userID: String, nickname: String, context: Context, func: () -> Unit) {
        SendBird.connect(userID) { user, e ->
            if (e != null) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            } else {
                SendBird.updateCurrentUserInfo(nickname, null) { e ->
                    if (e != null) {
                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                    }
                    func()
                }
            }
        }
    }

    fun openMessageChannelAndSendAcceptance(borrowerId: Long, context: Context, book: Book) {
        val TAG = "Notifications"
        Log.d(TAG, "creating message channel")
        val lenderId = LoginRepository.getUserId()

        SendBird.connect(lenderId.toString()) { user: User?, e: SendBirdException? ->
            if (user != null) {
                if (e != null) {
                    Log.e(TAG, e.message!!)
                } else {
                    Log.d(TAG, "Connection succeeded")
                    val params = GroupChannelParams()

                    val users = ArrayList<String>()
                    users.add(lenderId.toString())
                    users.add(borrowerId.toString())
                    Log.d(TAG, "Users: ${users}")
                    params.addUserIds(users)

                    GroupChannel.createChannelWithUserIds(users, true) { groupChannel: GroupChannel?, e: SendBirdException? ->
                        if (e != null) {
                            Log.e(TAG, "Channel creation failed")
                            Log.e(TAG, e.message!!)
                        }
                        val url = groupChannel?.url
                        Log.i(TAG, "Url: ${url}")

                        val params = UserMessageParams()
                            .setMessage("Borrow Request Accepted: ${book.title}")
                        groupChannel?.sendUserMessage(params,
                            BaseChannel.SendUserMessageHandler { _, e ->
                                if (e != null) {    // Error.
                                    return@SendUserMessageHandler
                                }
                                //adapter.addFirst(userMessage)
                            })


                        val intent = Intent(context, ChannelActivity::class.java)
                        intent.putExtra(EXTRA_CHANNEL_URL, url)
                        context.startActivity(intent)
                    }
                }
            } else {
                Log.e(TAG, "Connection failed: user null")
            }
        }
    }

    fun createChannelHandler(func: () -> Unit) {
        SendBird.getTotalUnreadChannelCount() { i: Int, sendBirdException: SendBirdException? ->
            Log.d("MessageService", i.toString())
            if (i > 0)
                func()
        }


    }
}