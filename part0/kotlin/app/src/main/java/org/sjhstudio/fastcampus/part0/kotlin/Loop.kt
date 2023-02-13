package org.sjhstudio.fastcampus.part0.kotlin

/**
 * 5. 반복문
 */

fun main() {
    // for 문
    for(i in 1..10) {
        print("$i ")
    }
    println()
    for (i in IntRange(1, 10)) {
        print("$i ")
    }
    println()
    for (i in 1 until 11) {
        print("$i ")
    }
    println()
    for (i in 10 downTo 1 step(1)) {
        print("$i ")
    }
    println()

    // while 문
    var i = 1
    while (i <= 10) {
        print("$i ")
        i++
    }
}