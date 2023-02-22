package org.sjhstudio.fastcampus.part0.kotlin

// Null
fun main() {
    var nickname: String? = null
    println(nickname ?: "없음")
    val size = nickname?.length
}