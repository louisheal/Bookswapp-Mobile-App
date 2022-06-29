package ac.ic.bookapp.messaging

import ac.ic.bookapp.R
import ac.ic.bookapp.data.LoginRepository
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sendbird.android.*

class ChannelListAdapter(listener: OnChannelClickedListener) : RecyclerView.Adapter<ChannelListAdapter.ChannelHolder>() {
    interface OnChannelClickedListener {
        fun onItemClicked(channel: GroupChannel)
    }

    private val listener: OnChannelClickedListener
    private var channels: MutableList<GroupChannel>


    init {
        channels = ArrayList()
        this.listener = listener
    }

    fun addChannels(channels: MutableList<GroupChannel>) {
        this.channels = channels
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ChannelHolder(layoutInflater.inflate(R.layout.item_channel_chooser, parent, false))    }

    override fun getItemCount() = channels.size


    override fun onBindViewHolder(holder: ChannelHolder, position: Int) {
        holder.bindViews(channels[position], listener)
    }

    class ChannelHolder(v: View) : RecyclerView.ViewHolder(v) {

        val channelName: TextView = v.findViewById(R.id.text_channel_name)
        val channelRecentMessage: TextView = v.findViewById(R.id.text_channel_recent)
        val channelMemberCount: TextView = v.findViewById(R.id.text_channel_member_count)
        val context = v.context


        fun bindViews(groupChannel: GroupChannel, listener: OnChannelClickedListener) {
            val lastMessage = groupChannel.lastMessage

            if (lastMessage != null) {

                when (lastMessage) {
                    is UserMessage -> channelRecentMessage.setText(lastMessage.message)
                    is AdminMessage -> channelRecentMessage.setText(lastMessage.message)
                    else -> {
                        val fileMessage = lastMessage as FileMessage
                        val sender = fileMessage.sender.nickname

                        channelRecentMessage.text = sender
                    }
                }
            }

            itemView.setOnClickListener {
                listener.onItemClicked(groupChannel)
            }

            channelName.text = getChannelName(groupChannel, context)
            channelMemberCount.text = groupChannel.unreadMessageCount.toString()//groupChannel.memberCount.toString()
        }

        private fun getChannelName(channel: GroupChannel, context: Context): String {
            val members: MutableList<Member> = mutableListOf()
            Log.d("ChannelSize", channel.memberCount.toString())
            for (member in channel.members) {
                Log.d("Channel", member.nickname)
                if (member.userId != LoginRepository.getUserId().toString()) {
                    members.add(member)
                }
            }
            return members.joinToString { it.nickname }
        }

    }

}