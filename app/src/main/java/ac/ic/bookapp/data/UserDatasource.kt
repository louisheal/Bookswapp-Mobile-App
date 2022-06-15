package ac.ic.bookapp.data

import ac.ic.bookapp.model.Book
import ac.ic.bookapp.model.User
import kotlinx.coroutines.runBlocking
import retrofit2.http.GET
import retrofit2.http.Path

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
}

interface UserService {

    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("users/{user_id}/books")
    suspend fun getUserBooks(@Path("user_id") userId: Long): List<Book>
}