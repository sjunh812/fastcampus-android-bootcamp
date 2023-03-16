package org.sjhstudio.fastcampus.part2.chapter4.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://api.github.com/"

    private fun getOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(AppInterceptor())
        .build()

    fun getRetrofit() = Retrofit.Builder()
        .client(getOkHttpClient())
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}