package ac.ic.bookapp.data

import ac.ic.bookapp.filesys.LoginPreferences
import ac.ic.bookapp.model.Loan
import android.content.Context
import kotlinx.coroutines.runBlocking
import retrofit2.http.*

data class LoanPost(
    val fromUserId: Long,
    val toUserId: Long,
    val bookId: Long,
    val copies: Int
)

object LoanDatasource : Datasource<LoanService>(LoanService::class.java) {

    fun getUserBorrowedBooks(userId: Long): List<Loan> {
        return runBlocking {
            service.getUserBorrowedBooks(userId)
        }
    }

    fun postUserBorrowing(context: Context, ownerId: Long, bookId: Long) {
        runBlocking {
            service.postUserBorrowing(
                LoanPost(ownerId, LoginPreferences.getUserLoginId(context), bookId, 1)
            )
        }
    }
}

interface LoanService {

    @GET("/loans")
    suspend fun getUserBorrowedBooks(@Query("to") userId: Long): List<Loan>

    @POST("/loans")
    suspend fun postUserBorrowing(@Body load: LoanPost)

}