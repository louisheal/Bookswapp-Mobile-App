package ac.ic.bookapp.messaging

import ac.ic.bookapp.R
import ac.ic.bookapp.databinding.ActivityChannelBinding
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sendbird.android.GroupChannel


class ChannelListActivity : AppCompatActivity(), ChannelListAdapter.OnChannelClickedListener {

    private val EXTRA_CHANNEL_URL = "EXTRA_CHANNEL_URL"

    lateinit var recyclerView: RecyclerView

    lateinit var adapter: ChannelListAdapter

    lateinit var binding: ActivityChannelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChannelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ChannelListAdapter(this)
        recyclerView = binding.recyclerGroupChannels
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        binding.fabGroupChannelCreate.setOnClickListener{
            val intent = Intent(this, ChannelCreateActivity::class.java)
            startActivity(intent)
        }

        addChannels()
    }

    private fun launchCreateChannel() {
        Log.d("ChannelList", "Button pressed")
        Toast.makeText(this, "Button pressed", Toast.LENGTH_SHORT).show()
    }

    private fun addChannels() {
        val channelList = GroupChannel.createMyGroupChannelListQuery()
        channelList.limit = 100
        channelList.next { list, e ->
            if (e != null) {
                Log.e("TAG", e.message!!)
            }
            adapter.addChannels(list)
        }
    }

    override fun onItemClicked(channel: GroupChannel) {
        val intent = Intent(this, ChannelActivity::class.java)
        intent.putExtra(EXTRA_CHANNEL_URL, channel.url)
        startActivity(intent)
    }
}
