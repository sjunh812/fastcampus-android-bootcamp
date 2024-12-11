package com.sjhstudio.compose.movieapp.features.common.repository

import com.sjhstudio.compose.movieapp.features.common.network.api.IMovieAppNetworkApi
import com.sjhstudio.compose.movieapp.features.common.network.model.MovieResponse
import com.sjhstudio.compose.movieapp.library.network.model.ApiResult
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieAppNetworkApi: IMovieAppNetworkApi
) : IMovieDataSource {

    override suspend fun getMovies(): ApiResult<List<MovieResponse>> {
        return movieAppNetworkApi.getMovies()
    }
}