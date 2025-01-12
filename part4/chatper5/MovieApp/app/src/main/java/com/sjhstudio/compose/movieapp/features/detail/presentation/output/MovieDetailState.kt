package com.sjhstudio.compose.movieapp.features.detail.presentation.output

import com.sjhstudio.compose.movieapp.features.common.entity.MovieDetailEntity

sealed class MovieDetailState {
    data object Initial : MovieDetailState()
    data class Main(val movieDetailEntity: MovieDetailEntity) : MovieDetailState()
}
