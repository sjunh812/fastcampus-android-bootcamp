package org.sjhstudio.chapter11.model

data class Home(
    val user: User,
    val appbarImageUrl: String
)

data class User(
    val nickname: String,
    val currentStarCount: String,
    val totalStarCount: String
)