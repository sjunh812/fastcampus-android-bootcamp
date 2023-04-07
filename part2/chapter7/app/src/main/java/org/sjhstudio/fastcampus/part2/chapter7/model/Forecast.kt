package org.sjhstudio.fastcampus.part2.chapter7.model

data class Forecast(
    val forecastDate: String,
    val forecastTime: String,
    var pop: Int? = null,
    var pty: String = "",
    var sky: String = "",
    var tmp: Int? = null,
) {

    val dateTime: String
        get() = "$forecastDate$forecastTime"

    val skyPty: String
        get() = pty.ifEmpty { sky }
}
