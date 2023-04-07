package org.sjhstudio.fastcampus.part2.chapter7.model

import com.google.gson.annotations.SerializedName
import org.sjhstudio.fastcampus.part2.chapter7.util.transformPtyCode
import org.sjhstudio.fastcampus.part2.chapter7.util.transformSkyCode

data class WeatherEntity(
    @SerializedName("response")
    val response: WeatherResponse
)

data class WeatherResponse(
    @SerializedName("header")
    val header: WeatherHeader,
    @SerializedName("body")
    val body: WeatherBody,
)

data class WeatherHeader(
    @SerializedName("resultCode")
    val resultCode: String,
    @SerializedName("resultMsg")
    val resultMessage: String,
)

data class WeatherBody(
    @SerializedName("items")
    val items: ForecastEntityList
)

data class ForecastEntityList(
    @SerializedName("item")
    val forecastEntities: List<ForecastEntity>
) {

    fun toForecastList(): List<Forecast> {
        val map = mutableMapOf<String, Forecast>()

        forecastEntities.forEach { forecastEntity ->
            val key = "${forecastEntity.forecastDate}/${forecastEntity.forecastTime}"
            val categoryValue = forecastEntity.forecastValue

            if (!map.containsKey(key)) {
                map[key] = Forecast(
                    forecastDate = forecastEntity.forecastDate,
                    forecastTime = forecastEntity.forecastTime,
                )
            }

            when (forecastEntity.category) {
                Category.POP -> map[key]?.pop = categoryValue.toIntOrNull()
                Category.PTY -> map[key]?.pty = transformPtyCode(categoryValue.toIntOrNull())
                Category.SKY -> map[key]?.sky = transformSkyCode(categoryValue.toIntOrNull())
                Category.TMP -> map[key]?.tmp = categoryValue.toIntOrNull()
                else -> {}
            }
        }

        return map.values.toMutableList().sortedWith { f1, f2 ->
            return@sortedWith f1.dateTime.compareTo(f2.dateTime)
        }
    }
}

data class ForecastEntity(
    @SerializedName("baseDate")
    val baseDate: String,
    @SerializedName("baseTime")
    val baseTime: String,
    @SerializedName("category")
    val category: Category?,
    @SerializedName("fcstDate")
    val forecastDate: String,
    @SerializedName("fcstTime")
    val forecastTime: String,
    @SerializedName("fcstValue")
    val forecastValue: String,
    @SerializedName("nx")
    val nx: Int,
    @SerializedName("ny")
    val ny: Int
)
