package ac.ic.bookapp.messaging

import ac.ic.bookapp.R
import ac.ic.bookapp.databinding.FragmentChannelListBinding
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sendbird.android.GroupChannel

class ChannelListFragment: Fragment(R.layout.fragment_channel_list), ChannelListAdapter.OnChannelClickedListener {
    private var _binding: FragmentChannelListBinding? = null
    private val binding get() = _binding!!

    private val EXTRA_CHANNEL_URL = "EXTRA_CHANNEL_URL"

    lateinit var recyclerView: RecyclerView

    lateinit var adapter: ChannelListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChannelListBinding.inflate(inflater, container, false)
        val view = binding.root

        adapter = ChannelListAdapter(this)
        recyclerView = binding.recyclerGroupChannels
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addChannels()
        removeBadge()
    }

    override fun onStart() {
        super.onStart()
        addChannels()
    }

    override fun onResume() {
        super.onResume()
        addChannels()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClicked(channel: GroupChannel) {
        val intent = Intent(context, ChannelActivity::class.java)
        intent.putExtra(EXTRA_CHANNEL_URL, channel.url)
        startActivity(intent)
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

    private fun removeBadge() {
        val nav = this.requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        nav.removeBadge(R.id.messagingFragment)
    }
}