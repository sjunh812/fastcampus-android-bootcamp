package com.sjhstudio.compose.movieapp.features.common.repository

import com.sjhstudio.compose.movieapp.features.common.network.model.MovieResponse
import com.sjhstudio.compose.movieapp.library.network.model.ApiResult

interface IMovieDataSource {
    suspend fun getMovies(): ApiResult<List<MovieResponse>>
}