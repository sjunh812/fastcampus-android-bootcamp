package org.sjhstudio.fastcampus.part2.chapter7.model

data class Forecast(
    val forecastDate: String,
    val forecastTime: String,
    var pop: Int? = null,
    var pty: String? = null,
    var sky: String? = null,
    var tmp: Int? = null,
)
