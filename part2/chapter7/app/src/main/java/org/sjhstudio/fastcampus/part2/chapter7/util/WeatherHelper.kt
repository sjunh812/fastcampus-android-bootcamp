package org.sjhstudio.fastcampus.part2.chapter7.util

fun transformPtyCode(code: Int?) =
    when (code) {
        1 -> "비"
        2 -> "비/눈"
        3 -> "눈"
        4 -> "소나기"
        else -> null
    }

fun transformSkyCode(code: Int?) =
    when (code) {
        1 -> "맑음"
        3 -> "구름많음"
        4 -> "흐림"
        else -> null
    }
