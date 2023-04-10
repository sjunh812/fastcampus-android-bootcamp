package org.sjhstudio.fastcampus.part2.chapter7.repository

import android.accounts.NetworkErrorException
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import org.sjhstudio.fastcampus.part2.chapter7.exception.EmptyBodyException
import org.sjhstudio.fastcampus.part2.chapter7.model.BaseDateTime
import org.sjhstudio.fastcampus.part2.chapter7.model.Forecast
import org.sjhstudio.fastcampus.part2.chapter7.model.WeatherEntity
import org.sjhstudio.fastcampus.part2.chapter7.network.Network
import org.sjhstudio.fastcampus.part2.chapter7.util.GeoPointConverter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

object WeatherRepository {

    private const val LOG = "WeatherRepository"

    fun getVillageForecast(
        latitude: Double,
        longitude: Double,
        successCallback: (List<Forecast>) -> Unit,
        failureCallback: (Throwable) -> Unit
    ) {
        val baseDateTime = BaseDateTime.getBaseDateTime()
        val geoPoint = GeoPointConverter.convert(latitude, longitude)

        Log.e(LOG, "$baseDateTime")
        Log.e(LOG, "${geoPoint.x} ${geoPoint.y}")

        Network.getWeatherService().getWeather(
            baseDate = baseDateTime.baseDate,
            baseTime = baseDateTime.baseTime,
            nx = geoPoint.x,
            ny = geoPoint.y
        ).enqueue(object : Callback<WeatherEntity> {
            override fun onResponse(call: Call<WeatherEntity>, response: Response<WeatherEntity>) {
                if (response.isSuccessful) {
                    val forecastEntities = response.body()?.response?.body?.items
                    val list = forecastEntities?.toForecastList()

                    if (list != null) successCallback.invoke(list)
                    else failureCallback.invoke(EmptyBodyException())
                } else {
                    failureCallback.invoke(NetworkErrorException())
                }
            }

            override fun onFailure(call: Call<WeatherEntity>, t: Throwable) {
                failureCallback.invoke(t)
            }
        })
    }

    fun getAddress(
        context: Context,
        latitude: Double,
        longitude: Double,
        callback: (Address?) -> Unit
    ) {
        Thread {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Geocoder(context, Locale.KOREA).getFromLocation(
                        latitude,
                        longitude,
                        1
                    ) { addressList ->
                        Handler(Looper.getMainLooper()).post {
                            callback.invoke(addressList.firstOrNull())
                        }
                    }
                } else {
                    val addressList =
                        Geocoder(context, Locale.KOREA).getFromLocation(latitude, longitude, 1)

                    Handler(Looper.getMainLooper()).post {
                        callback.invoke(addressList?.firstOrNull())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }
}