package org.sjhstudio.fastcampus.part0.kotlin

// Scope Function
fun main() {
    val userInfo: UserInfo? = UserInfo("서준형", 26, true)

    // Scope Function
    // let, run, apply, also, with

    // 1. let (local variable 사용, return 값으로 람다 마지막 행)
    // null-safe 처리에 많이 사용됨.
    val isChild = userInfo?.let { info -> !info.isAdult }

    // 2. run (return 값으로 람다 마지막 행)
    // 객체 초기화에 많이 사용됨.
    val child = userInfo?.run {
        age = 1
        isAdult = false
    }

    // 3. apply (return 값으로 수신객체)
    // 객체 초기화에 많이 사용됨.
    val male = userInfo?.apply {
        name = "승재"
    }

    // 4. also (local variable 사용, return 값으로 수신객체)
    // 수신객체의 로그를 확인할 때 많이 사용됨.
    val female = userInfo?.also { info -> println(info.age) }

    // 5. with (return 값으로 람다 마지막 행)
    // with(수신객체) { ... }
    val result = with(userInfo!!) {
        isAdult = false
        false
    }
}

class UserInfo(
    var name: String,
    var age: Int,
    var isAdult: Boolean
)