package ac.ic.bookapp.filesys

import ac.ic.bookapp.R
import android.content.Context
import android.content.SharedPreferences

object LoginPreferences {

    fun getUsername(context: Context): String {
        val key = context.resources.getString(R.string.login_username_key)
        val default = context.resources.getString(R.string.login_dev_username)
        return getPref(context).getString(key, default)!!
    }

    fun setUserLoginId(context: Context, userId: Long) {
        val key = context.resources.getString(R.string.login_user_id_key)
        val pref = getPref(context)
        with(pref.edit()) {
            putLong(key, userId)
            apply()
        }
    }

    fun getUserLoginId(context: Context): Long {
        val key = context.resources.getString(R.string.login_user_id_key)
        val default = 3.toLong()//context.resources.getString(R.string.login_dev_id).toLong()
        return getPref(context).getLong(key, default)
    }

    private fun getPref(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            context.resources.getString(R.string.login_shared_preferences_name),
            Context.MODE_PRIVATE
        )
    }
}