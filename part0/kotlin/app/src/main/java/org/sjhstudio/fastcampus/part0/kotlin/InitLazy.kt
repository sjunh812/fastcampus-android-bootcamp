package org.sjhstudio.fastcampus.part0.kotlin

// 초기화 지연
lateinit var name: String
val age: Int by lazy {
    println("초기화 중")
    100
}

fun main() {
    println("메인 함수 실행")
    println("초기화 한 값 $age")
    println("두번째 호출 $age")
    name = "서준형"
}