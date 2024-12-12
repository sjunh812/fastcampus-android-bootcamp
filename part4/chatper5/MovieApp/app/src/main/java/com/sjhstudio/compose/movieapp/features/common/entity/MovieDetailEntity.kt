package com.sjhstudio.compose.movieapp.features.common.entity

data class MovieDetailEntity(
    val actors: List<String> = emptyList(),
    val desc: String = "",
    val directors: List<String> = emptyList(),
    val imageUrl: String = "",
    val imdbPath: String = "",
    override val genres: List<String> = emptyList(),
    override val thumbUrl: String = "",
    override val title: String = "",
    override val rating: Float = 0F,
    override val year: Int? = null
) : MovieFeedItemEntity()

fun MovieDetailEntity.toMovieFeedItemEntity(): MovieFeedItemEntity =
    MovieFeedItemEntity(
        genres = genres,
        thumbUrl = thumbUrl,
        title = title,
        rating = rating,
        year = year
    )