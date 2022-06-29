package ac.ic.bookapp.ui.notifs

import ac.ic.bookapp.R
import ac.ic.bookapp.data.LoanDatasource
import ac.ic.bookapp.data.LoginRepository
import ac.ic.bookapp.databinding.FragmentNotifsBinding
import ac.ic.bookapp.model.LoanRequest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

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

        with(binding.notifsRefresh) {
            setOnRefreshListener {
                displayNotifs()
                isRefreshing = false
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "Loading notifs")
        displayNotifs()
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
            LoginRepository.getUserId()
        )

    fun displayRequestConfirmation(title: String, name: String) {
        val newFragment = RequestConfirmedDialogFragment(title, name)
        newFragment.show(parentFragmentManager, "dialog")
    }
}