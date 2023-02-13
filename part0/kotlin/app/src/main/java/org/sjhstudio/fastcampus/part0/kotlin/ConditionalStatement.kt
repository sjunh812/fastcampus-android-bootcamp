package org.sjhstudio.fastcampus.part0.kotlin

/**
 * 4. 조건식(Conditional Statement)
 */

fun main() {
    println(max(1, 2))
    isHoliday(4)
    isHoliday("월")
}

fun max(a: Int, b: Int) = if (a > b) a else b

fun isHoliday(dayOfWeek: Any) {
    val result = when (dayOfWeek) {
        "토", "일" -> "주말"
        in 2..4 -> "에러1"
        in listOf("월", "화", "수", "목", "금") -> "평일"
        else -> "에러2"
    }
    println(result)
}