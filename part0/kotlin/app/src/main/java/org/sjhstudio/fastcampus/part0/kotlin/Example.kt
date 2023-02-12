package org.sjhstudio.fastcampus.part0.kotlin

fun main() {
    val result = test(1, c = 5)
    test2(id = "준형", name = "서준형", nickname = "준형")
    println(result)
    println(test3(1, 3))
}

fun test(a: Int, b: Int = 3, c: Int = 4) : Int{
    return a + b + c
}

fun test2(name: String, nickname: String, id: String) = println("$name $nickname $id")

fun test3(a: Int, b: Int) = a * b