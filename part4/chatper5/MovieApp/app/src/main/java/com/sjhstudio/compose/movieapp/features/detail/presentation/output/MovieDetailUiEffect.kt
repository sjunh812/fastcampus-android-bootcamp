package com.sjhstudio.compose.movieapp.features.detail.presentation.output

sealed class MovieDetailUiEffect {
    data object Back : MovieDetailUiEffect()
    data class OpenUrl(val url: String) : MovieDetailUiEffect()
    data class OpenMovieRateDialog(val movieTitle: String, val rating: Float) : MovieDetailUiEffect()
}