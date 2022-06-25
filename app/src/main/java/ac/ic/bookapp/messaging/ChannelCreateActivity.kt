package ac.ic.bookapp.messaging

import ac.ic.bookapp.R
import ac.ic.bookapp.databinding.ActivityCreateBinding
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sendbird.android.GroupChannel
import com.sendbird.android.GroupChannelParams
import com.sendbird.android.SendBird
import com.sendbird.android.User

class ChannelCreateActivity : AppCompatActivity(), ChannelCreateAdapter.OnItemCheckedChangeListener {

    private val EXTRA_CHANNEL_URL = "EXTRA_CHANNEL_URL"


    private lateinit var selectedUsers: ArrayList<String>
    private lateinit var adapter: ChannelCreateAdapter
    private lateinit var recyclerView: RecyclerView

    lateinit var binding: ActivityCreateBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectedUsers = ArrayList()

        adapter = ChannelCreateAdapter(this)
        recyclerView = binding.recyclerCreate
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadUsers()
        binding.buttonCreate.setOnClickListener { createChannel(selectedUsers)}

    }

    private fun loadUsers() {
        val userListQuery = SendBird.createApplicationUserListQuery()

        userListQuery.next() { list, e ->
            if (e != null) {
                Log.e("TAG", e.message!!)
            } else {
                adapter.addUsers(list)
            }
        }
    }

    private fun createChannel(users: MutableList<String>) {
        val params = GroupChannelParams()

        val operatorId = ArrayList<String>()
        operatorId.add(SendBird.getCurrentUser().userId)

        params.addUserIds(users)
        params.setOperatorUserIds(operatorId)

        GroupChannel.createChannel(params) { groupChannel, e ->
            if (e != null) {
                Log.e("TAG", e.message!!)
            } else {
                val intent = Intent(this, ChannelActivity::class.java)
                intent.putExtra(EXTRA_CHANNEL_URL, groupChannel.url)
                startActivity(intent)
            }
        }
    }

    override fun onItemChecked(user: User, checked: Boolean) {
        if (checked) {
            selectedUsers.add(user.userId)
        } else {
            selectedUsers.remove(user.userId)
        }
    }
}