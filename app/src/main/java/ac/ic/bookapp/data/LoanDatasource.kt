package ac.ic.bookapp.data

import ac.ic.bookapp.model.Loan
import kotlinx.coroutines.runBlocking
import retrofit2.http.GET
import retrofit2.http.Query

data class LoanPost(
    val fromId: Long,
    val toId: Long,
    val bookId: Long,
    val copies: Int
)

object LoanDatasource : Datasource<LoanService>(LoanService::class.java) {

    fun getUserBorrowedBooks(userId: Long): List<Loan> {
        return runBlocking {
            service.getUserBorrowedBooks(userId)
        }
    }
}

interface LoanService {

    @GET("/loans")
    suspend fun getUserBorrowedBooks(@Query("to") userId: Long): List<Loan>
}