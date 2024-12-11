package com.sjhstudio.compose.movieapp.features.common.network.api

import com.sjhstudio.compose.movieapp.features.common.network.model.MovieResponse
import com.sjhstudio.compose.movieapp.library.network.model.ApiResult

interface IMovieAppNetworkApi {
    suspend fun getMovies(): ApiResult<List<MovieResponse>>
}