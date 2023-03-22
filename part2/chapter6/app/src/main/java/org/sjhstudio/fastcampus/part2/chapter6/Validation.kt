package org.sjhstudio.fastcampus.part2.chapter6

import java.util.regex.Pattern

object Validation {

    fun validateEmail(email: String): Boolean? {
        return true
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