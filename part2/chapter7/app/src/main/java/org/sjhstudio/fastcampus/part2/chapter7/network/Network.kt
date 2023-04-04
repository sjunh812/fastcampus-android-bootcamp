package org.sjhstudio.fastcampus.part2.chapter7.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Network {

    private const val BASE_URL = "http://apis.data.go.kr/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getWeatherService(): WeatherService {
        return getRetrofit().create(WeatherService::class.java)
    }
}