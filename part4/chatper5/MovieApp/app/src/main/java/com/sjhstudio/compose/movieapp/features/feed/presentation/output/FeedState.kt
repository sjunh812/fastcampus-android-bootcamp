package com.sjhstudio.compose.movieapp.features.feed.presentation.output

import com.sjhstudio.compose.movieapp.features.common.entity.CategoryEntity

sealed class FeedState {
    data object Loading : FeedState()
    data class Main(val movieList: List<CategoryEntity>) : FeedState()
    data class Failed(val reason: String) : FeedState()
}