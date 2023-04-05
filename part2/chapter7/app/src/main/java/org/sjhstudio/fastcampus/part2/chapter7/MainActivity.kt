package org.sjhstudio.fastcampus.part2.chapter7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.sjhstudio.fastcampus.part2.chapter7.model.BaseDateTime
import org.sjhstudio.fastcampus.part2.chapter7.model.WeatherEntity
import org.sjhstudio.fastcampus.part2.chapter7.network.Network
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    companion object {
        private const val LOG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiTest()
    }

    private fun apiTest() {
        val baseDateTime = BaseDateTime.getBaseDateTime()
        Log.e(LOG, "$baseDateTime")
        Network.getWeatherService().getWeather(
            baseDate = BaseDateTime.getBaseDateTime().baseDate,
            baseTime = BaseDateTime.getBaseDateTime().baseTime,
            nx = 55,
            ny = 127
        ).enqueue(object : Callback<WeatherEntity> {
            override fun onResponse(call: Call<WeatherEntity>, response: Response<WeatherEntity>) {
                if (response.isSuccessful) {
                    val forecastList = response.body()?.response?.body?.items
                    Log.e(LOG, "${forecastList?.toForecastMap()}")
                } else {
                    Log.e(LOG, "network error")
                }
            }

            override fun onFailure(call: Call<WeatherEntity>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}