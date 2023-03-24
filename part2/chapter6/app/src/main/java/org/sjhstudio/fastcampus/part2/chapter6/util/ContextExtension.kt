package org.sjhstudio.fastcampus.part2.chapter6.util

import android.content.Context
import android.widget.Toast

fun Context.showToastMessage(message: String, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message, duration).show()
