package ac.ic.bookapp.data

import ac.ic.bookapp.model.User
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.POST
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
object LoginDatasource : Datasource<LoginService>(LoginService::class.java) {

    private lateinit var authToken: String

    fun signup(user: User) {
        runBlocking {
            service.signup(user)
        }
    }

    fun login(username: String, password: String): Result<String> {
        return try {
            runBlocking {
                service.login(Login(username, password))
            }
            Result.Success(authToken)
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        val authHeader = response.header(AUTHORIZATION)
        if (authHeader != null) {
            authToken = response.header(AUTHORIZATION)!!
        }

        return response
    }
}

data class Login(
    private val username: String,
    private val password: String
)

interface LoginService {

    @POST("signup")
    suspend fun signup(@Body user: User)

    @POST("login")
    suspend fun login(@Body creds: Login)
}