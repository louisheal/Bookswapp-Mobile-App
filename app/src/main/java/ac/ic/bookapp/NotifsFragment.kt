package ac.ic.bookapp

import ac.ic.bookapp.data.LoanDatasource
import ac.ic.bookapp.databinding.FragmentNotifsBinding
import ac.ic.bookapp.filesys.LoginPreferences
import ac.ic.bookapp.messaging.MessageService
import ac.ic.bookapp.model.LoanRequest
import ac.ic.bookapp.recycleViewAdapters.NotifRowAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sendbird.android.SendBird

private const val TAG = "NotifsFragment"

class NotifsFragment : Fragment() {
    private var _binding: FragmentNotifsBinding? = null

    private val binding get() = _binding!!

    private lateinit var notifsList: RecyclerView

    private lateinit var emptyNotifsText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "View creation")
        _binding = FragmentNotifsBinding.inflate(inflater, container, false)
        val view = binding.root

        notifsList = binding.notifsList
        notifsList.setHasFixedSize(true)
        emptyNotifsText = binding.notifsEmptyText

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "Loading notifs")
        displayNotifs()
        SendBird.init("07375028-AE3C-4FC9-9D5D-428AE1B180B6", requireContext())

        binding.messageButton.setOnClickListener {
            MessageService.connectToSendBird("3", "Anand5329", requireContext())
        }
    }

    override fun onStart() {
        super.onStart()
        displayNotifs()
    }

    override fun onResume() {
        super.onResume()
        displayNotifs()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun displayNotifs() {
        val notifs = getLoanRequests()
        if (notifs.isEmpty()) {
            emptyNotifsText.visibility = View.VISIBLE
        } else {
            notifsList.adapter = NotifRowAdapter(this, getLoanRequests())
        }
        val nav = this.requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        nav.removeBadge(R.id.notifsFragment)
    }

    private fun getLoanRequests(): List<LoanRequest> =
        LoanDatasource.getUserIncomingLoanRequests(
            LoginPreferences.getUserLoginId(this.requireActivity())
        )

    fun displayRequestConfirmation(title: String, name: String) {
        val newFragment = RequestConfirmedDialogFragment(title, name)
        newFragment.show(parentFragmentManager, "dialog")
    }
}