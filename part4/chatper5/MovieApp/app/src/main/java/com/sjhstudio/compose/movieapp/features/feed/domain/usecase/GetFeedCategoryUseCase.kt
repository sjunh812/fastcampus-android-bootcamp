package com.sjhstudio.compose.movieapp.features.feed.domain.usecase

import com.sjhstudio.compose.movieapp.features.common.entity.CategoryEntity
import com.sjhstudio.compose.movieapp.features.common.entity.EntityWrapper
import com.sjhstudio.compose.movieapp.features.common.repository.IMovieDataSource
import com.sjhstudio.compose.movieapp.features.feed.domain.enum.SortOrder
import javax.inject.Inject

class GetFeedCategoryUseCase @Inject constructor(
    private val repository: IMovieDataSource
) : IGetFeedCategoryUseCase {

    override suspend fun invoke(sortOrder: SortOrder?): EntityWrapper<List<CategoryEntity>> {
        return repository.getCategories(sortOrder)
    }
}