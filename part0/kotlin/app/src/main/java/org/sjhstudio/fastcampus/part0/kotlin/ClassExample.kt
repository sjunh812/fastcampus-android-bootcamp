package org.sjhstudio.fastcampus.part0.kotlin


// Class
fun main() {
    val user = User("서준형", 10)
    println(user.age)
    val kid = Kid("아이", 3, "female")
}

open class User(open val name: String, open var age: Int = 100)

class Kid(override val name: String, override var age: Int) : User(name, age) {

    var gender: String = "male"

    // 클래스에서 제일 먼저 호출
    init {
        println("초기화 중 입니다.")
    }

    // 부생성자
    constructor(name: String, age: Int, gender: String) : this(name, age) {
        this.gender = gender
        println("부생성자 호출")
    }
}
