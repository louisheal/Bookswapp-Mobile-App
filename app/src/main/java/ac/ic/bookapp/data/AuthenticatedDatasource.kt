package ac.ic.bookapp.data

import okhttp3.Interceptor
import okhttp3.Response

const val AUTHORIZATION = "Authorization"
const val BEARER = "Bearer"

abstract class AuthenticatedDatasource<T>(
    apiClass: Class<T>
): Datasource<T>(apiClass) {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original
            .newBuilder()
            .addHeader(AUTHORIZATION, "$BEARER ${LoginRepository.authToken}")
            .method(original.method(), original.body())
            .build()
        return chain.proceed(request)
    }
}