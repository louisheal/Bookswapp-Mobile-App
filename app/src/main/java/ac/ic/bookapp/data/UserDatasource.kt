package ac.ic.bookapp.data

import ac.ic.bookapp.model.Book
import ac.ic.bookapp.model.Ownership
import ac.ic.bookapp.model.User
import kotlinx.coroutines.runBlocking
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

data class OwnershipPost(
    val bookId: Long,
    val totalCopies: Int,
    val currentCopies: Int
)

object UserDatasource : AuthenticatedDatasource<UserService>(UserService::class.java) {

    fun getCurrentUser(): User =
        runBlocking {
            service.getCurrentUser()
        }

    fun getUsers(): List<User> {
        return runBlocking {
            service.getUsers()
        }
    }

    fun getUserBooks(userId: Long): List<Book> {
        return runBlocking {
            service.getUserBooks(userId)
        }
    }

    fun getUserOwns(userId: Long): List<Ownership> {
        return runBlocking {
            service.getUserOwns(userId)
        }
    }

    fun postOwnership(userId: Long, bookId: Long, totalCopies: Int, currentCopies: Int) {
        runBlocking {
            service.postOwnership(userId, OwnershipPost(bookId, totalCopies, currentCopies))
        }
    }
}

interface UserService {

    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("users/current")
    suspend fun getCurrentUser(): User

    @GET("users/{user_id}/books")
    suspend fun getUserBooks(@Path("user_id") userId: Long): List<Book>

    @GET("users/{user_id}/owns")
    suspend fun getUserOwns(@Path("user_id") userId: Long): List<Ownership>

    @POST("users/{user_id}/owns")
    suspend fun postOwnership(@Path("user_id") userId: Long, @Body body: OwnershipPost): Unit
}