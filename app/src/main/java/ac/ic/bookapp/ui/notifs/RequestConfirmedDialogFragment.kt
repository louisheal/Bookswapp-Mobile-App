package ac.ic.bookapp.ui.notifs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class RequestConfirmedDialogFragment(val title: String, val name: String) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("You have lent \"$title\" to $name!\n" +
                    "Please meet at the Imperial Library tomorrow at 12pm.")
            builder.apply {
                setPositiveButton("Confirm") { dialog, _ ->
                    dialog.cancel()
                }
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null!")
    }
}
