package com.sjhstudio.compose.movieapp.features.detail.presentation.output

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface IMovieDetailViewModelOutputs {
    val detailState: StateFlow<MovieDetailState>
    val detailUiEffect: SharedFlow<MovieDetailUiEffect>
}