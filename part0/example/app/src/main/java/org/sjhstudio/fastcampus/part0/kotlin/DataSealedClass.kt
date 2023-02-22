package org.sjhstudio.fastcampus.part0.kotlin

// data class, sealed class
fun main() {
    println(Person("서준형", 26))
    println(Dog("초코", 5))

    val cat: Cat = BlueCat
    // 컴파일러가 Cat 의 자식을 알고있음. → else 가 필요없다.
    val result = when (cat) {
        is RedCat -> "red"
        is BlueCat -> "blue"
        is GreenCat -> "green"
        is WhiteCat -> "white"
    }
    println(result)
}

class Person(
    val name: String,
    val age: Int
)

data class Dog(
    val name: String,
    val age: Int
)

sealed class Cat
object RedCat : Cat()
object BlueCat : Cat()
object GreenCat : Cat()
object WhiteCat : Cat()