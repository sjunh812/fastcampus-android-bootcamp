package com.sjhstudio.compose.movieapp.features.common.repository

import com.sjhstudio.compose.movieapp.features.common.entity.CategoryEntity
import com.sjhstudio.compose.movieapp.features.common.entity.EntityWrapper
import com.sjhstudio.compose.movieapp.features.common.entity.MovieDetailEntity
import com.sjhstudio.compose.movieapp.features.common.network.api.IMovieAppNetworkApi
import com.sjhstudio.compose.movieapp.features.feed.data.mapper.CategoryMapper
import com.sjhstudio.compose.movieapp.features.feed.data.util.FeedConstants
import com.sjhstudio.compose.movieapp.features.feed.domain.enum.SortOrder
import com.sjhstudio.compose.movieapp.library.storage.IStorage
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieAppNetworkApi: IMovieAppNetworkApi,
    private val storage: IStorage,
    private val categoryMapper: CategoryMapper
) : IMovieDataSource {

    override suspend fun getCategories(sortOrder: SortOrder?): EntityWrapper<List<CategoryEntity>> {
        return categoryMapper.mapFromResult(
            result = movieAppNetworkApi.getMovies(),
            extra = sortOrder
        )
    }

    override suspend fun getMovieDetail(movieName: String): MovieDetailEntity {
        return storage.get<List<MovieDetailEntity>>(FeedConstants.MOVIE_LIST_KEY)
            ?.single { it.title == movieName } ?: MovieDetailEntity()
    }
}