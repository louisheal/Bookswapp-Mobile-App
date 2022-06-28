package ac.ic.bookapp.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Backend {

    private val moshi =
        Moshi
            .Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    val staging: Retrofit = createRetrofit("http://drp19-staging.herokuapp.com/")

    val production: Retrofit = createRetrofit("http://drp19.herokuapp.com/")

    private fun createRetrofit(url: String) =
        Retrofit
            .Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
}