package com.sjhstudio.compose.movieapp.features.common.entity

import com.sjhstudio.compose.movieapp.features.feed.domain.enum.SortOrder
import java.io.Serializable

open class MovieFeedItemEntity(
    open val genres: List<String> = emptyList(),
    open val thumbUrl: String = "",
    open val title: String = "",
    open val rating: Float = 0F,
    open val year: Int? = 0
) : Serializable

fun List<MovieFeedItemEntity>.toCategoryEntities(sortOrder: SortOrder): List<CategoryEntity> {
    val movieList = this
    val genreSet = mutableSetOf<String>().apply {
        addAll(movieList.flatMap { it.genres })
    }

    return mutableListOf<CategoryEntity>().also { categoryEntities ->
        genreSet.forEachIndexed { index, genreName ->
            movieList
                .filter { it.genres.contains(genreName) }
                .sortedByDescending {
                    when (sortOrder) {
                        SortOrder.RATING -> it.rating
                        SortOrder.YEAR -> it.year?.toFloat()
                    }
                }.run {
                    categoryEntities.add(
                        CategoryEntity(
                            id = index,
                            genre = genreName,
                            movieFeedEntities = this
                        )
                    )
                }
        }
    }
}