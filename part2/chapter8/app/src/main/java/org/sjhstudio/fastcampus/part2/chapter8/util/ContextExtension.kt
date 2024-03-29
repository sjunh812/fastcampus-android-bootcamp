package org.sjhstudio.fastcampus.part2.chapter8.util

import android.content.Context
import android.widget.Toast

fun Context.showToastMessage(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.dpToPx(dp: Int) = dp * resources.displayMetrics.density

fun Context.pxToDp(px: Float) = (px / resources.displayMetrics.density).toInt()