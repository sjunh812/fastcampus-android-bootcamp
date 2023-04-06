package org.sjhstudio.fastcampus.part2.chapter7.model

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

/**
 * 단기예보
 * - Base_time : 0200, 0500, 0800, 1100, 1400, 1700, 2000, 2300 (1일 8회)
 * - API 제공 시간(~이후) : 02:10, 05:10, 08:10, 11:10, 14:10, 17:10, 20:10, 23:10
 */
data class BaseDateTime(
    val baseDate: String,
    val baseTime: String,
) {

    companion object {

        fun getBaseDateTime(): BaseDateTime {
            val dateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"))

            val baseTime = when (dateTime.toLocalTime()) {
                in LocalTime.of(0, 0)..LocalTime.of(2, 15) -> {
                    dateTime.minusDays(1)
                    "2300"
                }
                in LocalTime.of(2, 15)..LocalTime.of(5, 15) -> "0200"
                in LocalTime.of(5, 15)..LocalTime.of(8, 15) -> "0500"
                in LocalTime.of(8, 15)..LocalTime.of(11, 15) -> "0800"
                in LocalTime.of(11, 15)..LocalTime.of(14, 15) -> "1100"
                in LocalTime.of(14, 15)..LocalTime.of(17, 15) -> "1400"
                in LocalTime.of(17, 15)..LocalTime.of(20, 15) -> "1700"
                in LocalTime.of(20, 15)..LocalTime.of(23, 15) -> "2000"
                else -> "2300"
            }

            // 20220101
            val baseDate = String.format(
                "%04d%02d%02d",
                dateTime.year,
                dateTime.monthValue,
                dateTime.dayOfMonth
            )

            return BaseDateTime(baseDate, baseTime)
        }
    }
}