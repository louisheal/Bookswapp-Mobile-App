package ac.ic.bookapp

import ac.ic.bookapp.data.LoanDatasource
import ac.ic.bookapp.databinding.FragmentMyBooksBinding
import ac.ic.bookapp.databinding.FragmentNotifsBinding
import ac.ic.bookapp.filesys.LoginPreferences
import ac.ic.bookapp.model.LoanRequest
import ac.ic.bookapp.recycleViewAdapters.NotifRowAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "NotifsFragment"

class NotifsFragment : Fragment() {
    private var _binding: FragmentNotifsBinding? = null

    private val binding get() = _binding!!

    private lateinit var notifsList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "View creation")
        _binding = FragmentNotifsBinding.inflate(inflater, container, false)
        val view = binding.root

        notifsList = view.findViewById(R.id.notifs_list)
        notifsList.setHasFixedSize(true)

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
        notifsList.adapter = NotifRowAdapter(this, getLoanRequests())
    }

    private fun getLoanRequests(): List<LoanRequest> =
        LoanDatasource.getUserIncomingLoanRequests(
            LoginPreferences.getUserLoginId(this.requireActivity())
        )
}