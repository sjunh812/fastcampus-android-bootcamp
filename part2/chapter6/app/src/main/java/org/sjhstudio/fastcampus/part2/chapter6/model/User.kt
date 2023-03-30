package org.sjhstudio.fastcampus.part2.chapter6.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val userId: String? = null,
    val userName: String? = null,
    val description: String? = null,    // 상태메시지
    val fcmToken: String? = null
)