package com.sjhstudio.compose.movieapp.features.feed.domain.di

import com.sjhstudio.compose.movieapp.features.feed.domain.usecase.GetFeedCategoryUseCase
import com.sjhstudio.compose.movieapp.features.feed.domain.usecase.IGetFeedCategoryUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun bindGetFeedCategoryUseCase(useCase: GetFeedCategoryUseCase): IGetFeedCategoryUseCase
}