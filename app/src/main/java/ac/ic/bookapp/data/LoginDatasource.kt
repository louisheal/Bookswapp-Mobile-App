package ac.ic.bookapp.data

import ac.ic.bookapp.model.User
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
object LoginDatasource {

    fun login(username: String, password: String): Result<User> {
        try {

            return Result.Error(Exception(""))
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}