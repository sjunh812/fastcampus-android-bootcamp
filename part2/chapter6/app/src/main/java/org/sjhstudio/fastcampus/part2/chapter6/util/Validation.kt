package org.sjhstudio.fastcampus.part2.chapter6.util

import java.util.regex.Pattern

object Validation {

    fun validateEmail(email: String): Boolean? {
        val emailRegex = "^[_a-zA-Z\\d-]+(\\.[_a-zA-Z\\d]+)*@[a-zA-Z\\d]+(\\.[a-zA-Z\\d]+)*(\\.[a-zA-Z]{2,})$"
        val emailPattern = Pattern.compile(emailRegex)

        return when {
            email.isEmpty() -> null
            !emailPattern.matcher(email).matches() -> false
            else -> true
        }
    }

    fun validatePassword(password: String): Boolean? {
        val pwRegex = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{8,20}$"
        val pwPattern = Pattern.compile(pwRegex)

        return when {
            password.isEmpty() -> null
            !pwPattern.matcher(password).matches() -> false
            else -> true
        }
    }
}