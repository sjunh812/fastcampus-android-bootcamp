package org.sjhstudio.fastcampus.part0.kotlin

// Lambda
fun main() {
    // 1. 익명함수
    // 2. 변수처럼 사용되서, 함수의 argument, return 값으로 사용 가능
    // 3. 한번 사용되고, 재사용되지 않는 함수
    val a = fun() { println("hello") }
    val b: (Int) -> Int = { it * 10 }
    val c = { i: Int, j: Int -> i * j }
    val d: (Int, String, Boolean) -> String = { _, s, _ -> s }

    hello(10, b)
}

fun hello(a: Int, b: (Int) -> Int): (Int) -> Int {
    println(a)
    println(b(10))
    return b
}