package org.sjhstudio.fastcampus.part0.kotlin

// Extension Function
fun main() {
    val a = MyClass()
    a.fuc1()
    a.fuc2()
    a.fuc3()    // MyClass 클래스에 대한 확장함수
}

class MyClass {

    fun fuc1() = println("1")
    fun fuc2() = println("2")
}

fun MyClass.fuc3() = println("3")