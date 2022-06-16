package ac.ic.bookapp.data

import ac.ic.bookapp.model.Book
import ac.ic.bookapp.model.User
import com.squareup.moshi.Json
import kotlinx.coroutines.runBlocking
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

data class OwnershipPost(
    @Json(name = "book_id") val bookId: Long,
    @Json(name = "total_copies") val totalCopies: Int,
    @Json(name = "current_copies") val currentCopies: Int
)

object UserDatasource : Datasource<UserService>(UserService::class.java) {

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

    fun postOwnership(userId: Long, bookId: Long, totalCopies: Int, currentCopies: Int) {
        runBlocking {
            service.postOwnership(userId, OwnershipPost(bookId, totalCopies, currentCopies))
        }
    }
}

interface UserService {

    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("users/{user_id}/books")
    suspend fun getUserBooks(@Path("user_id") userId: Long): List<Book>

    @POST("users/{user_id}/owns")
    suspend fun postOwnership(@Path("user_id") userId: Long, @Body body: OwnershipPost): Unit
}