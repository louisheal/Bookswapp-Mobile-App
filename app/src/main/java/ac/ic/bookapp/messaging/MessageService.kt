package ac.ic.bookapp.messaging

import ac.ic.bookapp.AddBookActivity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.sendbird.android.OpenChannel
import com.sendbird.android.SendBird
import com.sendbird.android.SendBirdException

object MessageService {

    val EXTRA_CHANNEL_URL: String = "EXTRA_CHANNEL_URL"

    fun connectToSendBird(userID: String, nickname: String, context: Context) {
        SendBird.connect(userID) { user, e ->
            if (e != null) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            } else {
                SendBird.updateCurrentUserInfo(nickname, null) { e ->
                    if (e != null) {
                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                    }
//                    OpenChannel.createChannel(OpenChannel.OpenChannelCreateHandler() {channel: OpenChannel, e: SendBirdException? ->
//                        if (e != null) {
//                            Log.e("Channel", e.message!!)
//                        }
//                        OpenChannel.getChannel(channel.url, OpenChannel.OpenChannelGetHandler {  })
//                    })
                    val intent = Intent(context, ChannelListActivity::class.java)
                    context.startActivity(intent)
                }
            }
        }
    }
}