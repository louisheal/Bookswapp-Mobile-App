package ac.ic.bookapp.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private val URL = Backend.PRODUCTION.url

abstract class Datasource<T>(
    private val apiClass: Class<T>
) {

    private val moshi =
        Moshi
            .Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    private val httpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(::intercept)
            .build()

    private val retrofit =
        Retrofit
            .Builder()
            .baseUrl(URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(httpClient)
            .build()

    protected val service: T by lazy {
        retrofit.create(apiClass)
    }

    protected abstract fun intercept(chain: Interceptor.Chain): Response
}