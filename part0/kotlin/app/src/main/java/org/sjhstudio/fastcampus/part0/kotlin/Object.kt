package org.sjhstudio.fastcampus.part0.kotlin

import org.sjhstudio.fastcampus.part0.kotlin.Book.Companion.NAME

// object, companion object
fun main() {
    println(Counter.count)

    Counter.up()
    Counter.up()
    Counter.down()

    println(Counter.count)
    println(Counter.hello())

    println(NAME)
}

object Counter : Hello() {

    var count: Int = 0

    init {
        println("카운터 초기화")
    }

    fun up() = count++

    fun down() = count--
}

open class Hello {

    fun hello() = println("hello")
}

class Book {

    companion object {  // object Companion
        const val NAME = "name"

        fun builder() = Book()
    }
}