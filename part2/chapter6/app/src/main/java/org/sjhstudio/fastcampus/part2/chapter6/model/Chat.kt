package org.sjhstudio.fastcampus.part2.chapter6.model

data class Chat(
    private val chatId: String,
    private val otherUserName: String,
    private val message: String
)