package org.sjhstudio.fastcampus.part0.kotlin

// Type check, Casting
fun main() {
    println(check("a"))
    println(check(1))
    println(check(true))
    cast(false)
    println(smartCast("안녕"))
    println(smartCast(100))
    println(smartCast(true))
}

fun check(a: Any): String {
    return if (a is String) "String"
    else if (a is Int) "Int"
    else "?"
}

fun cast(a: Any) {
    val result = (a as? String) ?: "실패"
    println(result)
}

fun smartCast(a: Any): Int {
    return when (a) {
        is String -> a.length
        is Int -> a.dec()
        else -> -1
    }
}