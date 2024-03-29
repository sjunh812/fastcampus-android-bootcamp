package org.sjhstudio.flow.chapter10.util

import java.util.regex.Pattern

/**
 * 정규식
 * '+' : 1개 이상
 * '*' : 0개 이상
 * 'd' : 숫자 0 ~ 9
 */
object Validation {

    fun validateEmail(email: String): Boolean? {
        val emailRegex =
            "^[a-zA-Z\\d]+(\\.[a-zA-Z\\d]+)*@[a-zA-Z\\d]+(\\.[a-zA-Z\\d]+)*(\\.[a-zA-Z]{2,})$"
        val emailPattern = Pattern.compile(emailRegex)

        return when {
            email.isEmpty() -> null
            !emailPattern.matcher(email).matches() -> false
            else -> true
        }
    }

    fun validatePassword(password: String): Boolean? {
        val passwordRegex = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$"
        val passwordPattern = Pattern.compile(passwordRegex)

        return when {
            password.isEmpty() -> null
            !passwordPattern.matcher(password).matches() -> false
            else -> true
        }
    }
}