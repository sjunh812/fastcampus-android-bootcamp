package org.sjhstudio.fastcampus.part2.chapter6.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Chat(
    val chatId: String? = null,
    val message: String? = null,
    val userId: String? = null
)