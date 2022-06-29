package ac.ic.bookapp

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class SwipeRefreshLayoutWithEmpty(context: Context, attributes: AttributeSet? = null) :
    SwipeRefreshLayout(context, attributes) {

//    constructor(context: Context, attributes: AttributeSet): this(context, attributes)

    private var container: ViewGroup? = null

    override fun canChildScrollUp(): Boolean {

        val container = getContainer() ?: return false;

        // The container has 2 children; the empty view and the scrollable view.
        if (container.childCount != 2) {
            throw RuntimeException("Container must have an empty view and content view");
        }

        // Use whichever one is visible and test that it can scroll
        var view: View = container.getChildAt(0);
        if (view.visibility != View.VISIBLE) {
            view = container.getChildAt(1);
        }

        return ViewCompat.canScrollVertically(view, -1);
    }

    private fun getContainer(): ViewGroup? {
        // Cache this view
        if (container != null) {
            return container;
        }

        // The container may not be the first view. Need to iterate to find it
        for (i in 0..childCount) {
            if (getChildAt(i) is ViewGroup) {
                container = getChildAt(i) as ViewGroup
                break;
            }
        }

        if (container == null) {
            throw RuntimeException("Container view not found");
        }

        return container;
    }
}