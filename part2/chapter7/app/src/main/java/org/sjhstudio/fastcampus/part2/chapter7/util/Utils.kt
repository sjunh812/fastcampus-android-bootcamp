package org.sjhstudio.fastcampus.part2.chapter7.util

import java.text.SimpleDateFormat
import java.util.*

val forecastTimeFormat = SimpleDateFormat("HHmm", Locale.KOREA)
val timeFormat = SimpleDateFormat("a h시", Locale.KOREA)

fun formatTime(time: String): String {
    val date = forecastTimeFormat.parse(time) ?: return ""
    return timeFormat.format(date)
}