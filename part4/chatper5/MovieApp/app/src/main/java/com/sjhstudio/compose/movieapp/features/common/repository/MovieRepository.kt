package com.sjhstudio.compose.movieapp.features.common.repository

import com.sjhstudio.compose.movieapp.features.common.entity.CategoryEntity
import com.sjhstudio.compose.movieapp.features.common.entity.EntityWrapper
import com.sjhstudio.compose.movieapp.features.common.network.api.IMovieAppNetworkApi
import com.sjhstudio.compose.movieapp.features.feed.domain.enum.SortOrder
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieAppNetworkApi: IMovieAppNetworkApi
) : IMovieDataSource {

    override suspend fun getCategories(sortOrder: SortOrder?): EntityWrapper<List<CategoryEntity>> {

    }

}