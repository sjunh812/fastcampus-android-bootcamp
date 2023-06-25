package org.sjhstudio.chapter11.model

data class Home(
    val user: User,
    val appbarImageUrl: String,
    val banner: Banner
)

data class User(
    val nickname: String,
    val currentStarCount: Int,
    val totalStarCount: Int
)

data class Banner(
    val imageUrl: String,
    val contentDescription: String
)