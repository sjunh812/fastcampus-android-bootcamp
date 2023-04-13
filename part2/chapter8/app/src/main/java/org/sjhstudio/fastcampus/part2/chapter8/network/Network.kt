package org.sjhstudio.fastcampus.part2.chapter8.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import org.sjhstudio.fastcampus.part2.chapter8.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Network {

    fun getOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(AppInterceptor())
        .build()

    fun getRetrofit(): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(getOkHttpClient())
            .build()
    }
}