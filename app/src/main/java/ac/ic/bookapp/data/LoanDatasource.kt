package ac.ic.bookapp.data

import ac.ic.bookapp.model.Loan
import ac.ic.bookapp.model.LoanRequest
import ac.ic.bookapp.model.Return
import kotlinx.coroutines.runBlocking
import retrofit2.http.*

data class LoanRequestPost(
    val fromUserId: Long,
    val toUserId: Long,
    val bookId: Long,
    val copies: Int
)

data class DecisionPost(
    val accept: Boolean
)

data class ReturnPost(
    val loanId: Long,
    val copies: Int
)

object LoanDatasource : AuthenticatedDatasource<LoanService>(LoanService::class.java) {

    fun getUserBorrowedBooks(userId: Long): List<Loan> {
        return runBlocking {
            service.getUserBorrowedBooks(userId)
        }
    }

    fun getUserLentBooks(userId: Long): List<Loan> {
        return runBlocking {
            service.getUserLentBooks(userId)
        }
    }

    fun getUserIncomingLoanRequests(userId: Long): List<LoanRequest> {
        return runBlocking {
            service.getIncomingLoanRequests(userId)
        }
    }

    fun getLoanReturns(loanId: Long): List<Return>  {
        return runBlocking {
            service.getReturnsOfLoan(loanId)
        }
    }

    fun postUserLoanRequest(userId: Long, ownerId: Long, bookId: Long) {
        runBlocking {
            service.postUserLoanRequest(
                LoanRequestPost(ownerId, userId, bookId, 1)
            )
        }
    }

    fun postLoanRequestDecision(requestId: Long, accept: Boolean) {
        runBlocking {
            service.postLoanRequestDecision(
                requestId,
                DecisionPost(accept)
            )
        }
    }

    fun postLoanReturn(loanId: Long) {
        runBlocking {
            service.postReturn(
                loanId,
                ReturnPost(loanId, 1)
            )
        }
    }
}

interface LoanService {

    @GET("/loans")
    suspend fun getUserBorrowedBooks(@Query("to") userId: Long): List<Loan>

    @GET("/loans")
    suspend fun getUserLentBooks(@Query("from") userId: Long): List<Loan>

    @GET("/loans/requests")
    suspend fun getIncomingLoanRequests(@Query("from") userId: Long): List<LoanRequest>

    @GET("/loans/{loan_id}/returns")
    suspend fun getReturnsOfLoan(@Path("loan_id") loanId: Long): List<Return>

    @POST("/loans/requests")
    suspend fun postUserLoanRequest(@Body load: LoanRequestPost)

    @POST("/loans/requests/{request_id}")
    suspend fun postLoanRequestDecision(
        @Path("request_id") requestId: Long,
        @Body decision: DecisionPost
    )

    @POST("/loans/{loan_id}/returns")
    suspend fun postReturn(
        @Path("loan_id") loanId: Long,
        @Body ret: ReturnPost
    )
}