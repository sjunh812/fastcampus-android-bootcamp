package com.sjhstudio.compose.movieapp.features.detail.domain.di

import com.sjhstudio.compose.movieapp.features.detail.domain.usecase.GetMovieDetailUseCase
import com.sjhstudio.compose.movieapp.features.detail.domain.usecase.IGetMovieDetailUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class DetailDomainModule {

    @Binds
    @ViewModelScoped
    abstract fun bindGetMovieDetailUseCase(useCase: GetMovieDetailUseCase): IGetMovieDetailUseCase
}