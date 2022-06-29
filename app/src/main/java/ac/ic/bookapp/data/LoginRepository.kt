package ac.ic.bookapp.data

import ac.ic.bookapp.model.User

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
object LoginRepository {

    private var _authToken: String? = null
    var authToken: String = ""
        private set
        get() = _authToken!!

    private var loggedIn: User? = null

    val isLoggedIn: Boolean
        get() = loggedIn != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        loggedIn = null
    }

    fun logout() {
        loggedIn = null
        LoginDatasource.logout()
    }

    fun login(username: String, password: String): Result<User> {
        val authResult = LoginDatasource.login(username, password)

        if (authResult is Result.Success) {
            _authToken = authResult.data
            setLoggedInUser(
                UserDatasource.getCurrentUser()
            )
            return Result.Success(loggedIn!!)
        }

        return Result.Error(Exception("Could not log in."))
    }
    
    fun getUserId() = loggedIn!!.id

    fun getUsername() = loggedIn!!.username

    private fun setLoggedInUser(loggedInUser: User) {
        this.loggedIn = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}