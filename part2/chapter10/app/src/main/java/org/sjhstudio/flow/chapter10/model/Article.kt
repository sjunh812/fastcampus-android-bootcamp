package org.sjhstudio.flow.chapter10.model

data class Article(
    val id: String,
    val imageUrl: String,
    val description: String,
    var isBookmark: Boolean
)