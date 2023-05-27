package org.sjhstudio.flow.chapter10.model

data class ArticleDto(
    val id: String? = null,
    val createAt: Long? = null,
    val imageUrl: String? = null,
    val description: String? = null
) {

    fun toArticle(isBookmark: Boolean): Article {
        return Article(
            id = id.orEmpty(),
            imageUrl = imageUrl.orEmpty(),
            description = description.orEmpty(),
            isBookmark = isBookmark
        )
    }
}
