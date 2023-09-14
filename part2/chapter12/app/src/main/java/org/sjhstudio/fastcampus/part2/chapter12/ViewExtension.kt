package org.sjhstudio.fastcampus.part2.chapter12

import android.view.View

fun View.containTouchArea(x: Int, y: Int): Boolean {
    return (x in left..right && y in top..bottom)
}