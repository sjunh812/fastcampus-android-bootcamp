package com.sjhstudio.compose.movieapp.features.detail.domain.usecase

import com.sjhstudio.compose.movieapp.features.common.entity.MovieDetailEntity

interface IGetMovieDetailUseCase {
    suspend operator fun invoke(movieName: String): MovieDetailEntity
}