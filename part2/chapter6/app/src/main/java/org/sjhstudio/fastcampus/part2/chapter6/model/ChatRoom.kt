package org.sjhstudio.fastcampus.part2.chapter6.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ChatRoom(
    val chatRoomId: String? = null,
    val lastMessage: String? = null,
    val otherUserName: String? = null,
    val otherUserId: String? = null
)