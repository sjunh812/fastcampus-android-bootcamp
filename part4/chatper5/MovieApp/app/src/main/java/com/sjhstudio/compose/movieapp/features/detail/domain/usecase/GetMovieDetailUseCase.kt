package com.sjhstudio.compose.movieapp.features.detail.domain.usecase

import com.sjhstudio.compose.movieapp.features.common.entity.MovieDetailEntity
import com.sjhstudio.compose.movieapp.features.common.repository.IMovieDataSource
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val repository: IMovieDataSource
) : IGetMovieDetailUseCase {
    override suspend fun invoke(movieName: String): MovieDetailEntity {
        return repository.getMovieDetail(movieName)
    }
}