package ac.ic.bookapp.ui.myBooks

import ac.ic.bookapp.databinding.FragmentMyBooksBinding
import ac.ic.bookapp.myBooksTabFragments.BorrowedBooksFragment
import ac.ic.bookapp.myBooksTabFragments.LentBooksFragment
import ac.ic.bookapp.myBooksTabFragments.OwnedBooksFragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator

private const val TAG = "MyBooksFragment"

class MyBooksFragment : Fragment() {
    private var _binding: FragmentMyBooksBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "View creation")
        _binding = FragmentMyBooksBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "Setting MyBooks tabs")

        val tabLayout = binding.bookTabs
        val viewPager = binding.booksPager
        val pagerAdapter = MyBooksPagerAdapter(requireActivity())

        pagerAdapter.addFragment(OwnedBooksFragment(), "Owned")
        pagerAdapter.addFragment(LentBooksFragment(), "Lent")
        pagerAdapter.addFragment(BorrowedBooksFragment(), "Borrowed")

        viewPager.adapter = pagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = pagerAdapter.getTitle(position)
        }.attach()
    }
}