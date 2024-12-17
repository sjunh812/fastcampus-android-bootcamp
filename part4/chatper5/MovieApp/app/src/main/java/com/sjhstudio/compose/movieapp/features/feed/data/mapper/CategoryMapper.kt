package com.sjhstudio.compose.movieapp.features.feed.data.mapper

import com.sjhstudio.compose.movieapp.features.common.entity.CategoryEntity
import com.sjhstudio.compose.movieapp.features.common.entity.EntityWrapper
import com.sjhstudio.compose.movieapp.features.common.entity.MovieDetailEntity
import com.sjhstudio.compose.movieapp.features.common.entity.toCategoryEntities
import com.sjhstudio.compose.movieapp.features.common.entity.toMovieFeedItemEntity
import com.sjhstudio.compose.movieapp.features.common.mapper.BaseMapper
import com.sjhstudio.compose.movieapp.features.common.network.model.MovieResponse
import com.sjhstudio.compose.movieapp.features.common.network.model.toMovieDetailEntity
import com.sjhstudio.compose.movieapp.features.feed.data.util.FeedConstants
import com.sjhstudio.compose.movieapp.features.feed.domain.enum.SortOrder
import com.sjhstudio.compose.movieapp.library.storage.IStorage
import javax.inject.Inject

class CategoryMapper @Inject constructor(
    private val storage: IStorage
) : BaseMapper<List<MovieResponse>, List<CategoryEntity>>() {

    override fun getSuccess(response: List<MovieResponse>?, extra: Any?): EntityWrapper.Success<List<CategoryEntity>> {
        return response?.let {
            EntityWrapper.Success(
                entity = mutableListOf<MovieDetailEntity>()
                    .apply {
                        addAll(response.map { it.toMovieDetailEntity() })
                    }.also {
                        storage.save(key = FeedConstants.MOVIE_LIST_KEY, it)
                    }.map {
                        it.toMovieFeedItemEntity()
                    }.toCategoryEntities(sortOrder = if (extra is SortOrder) extra else SortOrder.RATING)
            )
        } ?: EntityWrapper.Success(entity = emptyList())
    }

    override fun getFail(error: Throwable): EntityWrapper.Fail<List<CategoryEntity>> {
        return EntityWrapper.Fail(error = error)
    }
}