package com.sjhstudio.compose.movieapp.features.common.di

import com.sjhstudio.compose.movieapp.features.common.network.api.IMovieAppNetworkApi
import com.sjhstudio.compose.movieapp.features.common.network.api.MovieAppNetworkApi
import com.sjhstudio.compose.movieapp.features.common.repository.IMovieDataSource
import com.sjhstudio.compose.movieapp.features.common.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieDataModule {

    @Binds
    @Singleton
    abstract fun bindMovieAppNetworkApi(impl: MovieAppNetworkApi): IMovieAppNetworkApi

    @Binds
    @Singleton
    abstract fun bindMovieRepository(impl: MovieRepository): IMovieDataSource
}