package org.sjhstudio.fastcampus.part2.chapter9.util

import java.util.regex.Pattern

object Validation {

    fun validateEmail(email: String): Boolean? {
        val emailRegex =
            "^[a-zA-Z\\d_-]+(\\.[a-zA-Z\\d]+)*@[a-zA-Z\\d]+(\\.[a-zA-Z\\d]+)*\\.[a-zA-Z]{2,}$"
        val emailPattern = Pattern.compile(emailRegex)

        return when {
            email.isEmpty() -> null
            emailPattern.matcher(email).matches().not() -> false
            else -> true
        }
    }
}