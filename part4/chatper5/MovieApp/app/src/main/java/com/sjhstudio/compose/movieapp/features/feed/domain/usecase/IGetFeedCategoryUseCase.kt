package com.sjhstudio.compose.movieapp.features.feed.domain.usecase

import com.sjhstudio.compose.movieapp.features.common.entity.CategoryEntity
import com.sjhstudio.compose.movieapp.features.common.entity.EntityWrapper
import com.sjhstudio.compose.movieapp.features.feed.domain.enum.SortOrder

interface IGetFeedCategoryUseCase {
    suspend operator fun invoke(sortOrder: SortOrder? = null): EntityWrapper<List<CategoryEntity>>
}