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

    val staging: Retrofit =
        Retrofit
            .Builder()
            .baseUrl("http://drp19-staging.herokuapp.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
}