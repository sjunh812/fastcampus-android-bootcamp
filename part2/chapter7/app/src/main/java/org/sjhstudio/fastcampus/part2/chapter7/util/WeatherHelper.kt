package org.sjhstudio.fastcampus.part2.chapter7.util

import android.widget.ImageView
import org.sjhstudio.fastcampus.part2.chapter7.R

fun transformPtyCode(code: Int?) =
    when (code) {
        1 -> "비"
        2 -> "비/눈"
        3 -> "눈"
        4 -> "소나기"
        else -> ""
    }

fun transformSkyCode(code: Int?) =
    when (code) {
        1 -> "맑음"
        3 -> "구름많음"
        4 -> "흐림"
        else -> ""
    }

fun transformSkyPtyImageResource(skyPty: String) =
    when (skyPty) {
        "맑음" -> R.drawable.ic_sun
        "구름많음" -> R.drawable.ic_cloud
        "흐림" -> R.drawable.ic_overcast
        "비" -> R.drawable.ic_rain
        "비/눈" -> R.drawable.ic_sleet
        "눈" -> R.drawable.ic_snow
        "소나기" -> R.drawable.ic_rain
        else -> 0
    }

fun ImageView.setSkyPtyImageResource(skyPty: String) {
    when (skyPty) {
        "맑음" -> setImageResource(R.drawable.ic_sun)
        "구름많음" -> setImageResource(R.drawable.ic_cloud)
        "흐림" -> setImageResource(R.drawable.ic_overcast)
        "비" -> setImageResource(R.drawable.ic_rain)
        "비/눈" -> setImageResource(R.drawable.ic_sleet)
        "눈" -> setImageResource(R.drawable.ic_snow)
        "소나기" -> setImageResource(R.drawable.ic_rain)
        else -> setImageResource(0)
    }
}