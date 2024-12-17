package com.sjhstudio.compose.movieapp.features.feed.presentation.output

sealed class FeedUiEffect {
    data class OpenMovieDetail(val movieName: String) : FeedUiEffect()
    data object OpenInfoDialog : FeedUiEffect()
}