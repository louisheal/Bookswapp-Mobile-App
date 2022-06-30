package ac.ic.bookapp.ui.myBooks

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyBooksPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {

    private val fragments: MutableList<Fragment> = mutableListOf()
    private val titles: MutableList<String> = mutableListOf()

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment =
        fragments[position]

    fun addFragment(fragment: Fragment, title: String) {
        fragments += fragment
        titles += title
    }

    fun getTitle(position: Int): String = titles[position]
}