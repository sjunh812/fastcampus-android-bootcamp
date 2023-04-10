package org.sjhstudio.fastcampus.part2.chapter7.network

import org.sjhstudio.fastcampus.part2.chapter7.model.WeatherEntity
import org.sjhstudio.fastcampus.part2.chapter7.util.Constants.WEATHER_API_SERVICE_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("/1360000/VilageFcstInfoService_2.0/getVilageFcst?serviceKey=${WEATHER_API_SERVICE_KEY}&dataType=JSON")
    fun getWeather(
        @Query("base_date") baseDate: String,
        @Query("base_time") baseTime: String,
        @Query("nx") nx: Int,
        @Query("ny") ny: Int,
        @Query("numOfRows") numOfRows: Int = 336,
    ): Call<WeatherEntity>
}