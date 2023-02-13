package org.sjhstudio.fastcampus.part0.kotlin

/**
 * 1. 함수(Function)
 */

fun main() {
    val result = test(1, c = 5)
    test2(id = "준형", name = "서준형", nickname = "준형")
    println(result)
    println(test3(1, 3))
}

// 매개변수의 초기값을 설정하여, 자바의 오버로딩을 방지할 수 있다!
fun test(a: Int, b: Int = 3, c: Int = 0): Int {
    return a + b + c
}

// 리턴값이 없는 경우 실제 코틀린에서는 `Unit`이라는 타입을 가진다.(자바의 void)
fun test2(name: String, nickname: String, id: String) = println("$name $nickname $id")

// single expression(trailing comma 는 컴파일 에러가 발생하지 않는다!)
fun test3(a: Int, b: Int) = a * b