package org.sjhstudio.chapter11.model

data class Home(
    val user: User,
    val appbarImageUrl: String,
    val banner: Banner
)

data class User(
    val nickname: String,
    val currentStarCount: String,
    val totalStarCount: String
)

data class Banner(
    val imageUrl: String,
    val contentDescription: String
)