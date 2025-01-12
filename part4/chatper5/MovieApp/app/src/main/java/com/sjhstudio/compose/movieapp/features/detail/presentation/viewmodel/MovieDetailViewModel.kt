package com.sjhstudio.compose.movieapp.features.detail.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.sjhstudio.compose.movieapp.features.detail.domain.usecase.IGetMovieDetailUseCase
import com.sjhstudio.compose.movieapp.features.detail.presentation.input.IMovieDetailViewModelInputs
import com.sjhstudio.compose.movieapp.features.detail.presentation.output.IMovieDetailViewModelOutputs
import com.sjhstudio.compose.movieapp.features.detail.presentation.output.MovieDetailState
import com.sjhstudio.compose.movieapp.features.detail.presentation.output.MovieDetailUiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: IGetMovieDetailUseCase
) : ViewModel(), IMovieDetailViewModelInputs, IMovieDetailViewModelOutputs {

    val inputs: IMovieDetailViewModelInputs = this
    val outputs: IMovieDetailViewModelOutputs = this

    private val _detailState = MutableStateFlow<MovieDetailState>(MovieDetailState.Initial)
    override val detailState: StateFlow<MovieDetailState>
        get() = _detailState

    private val _detailUiEffect = MutableSharedFlow<MovieDetailUiEffect>(replay = 0)
    override val detailUiEffect: SharedFlow<MovieDetailUiEffect>
        get() = _detailUiEffect

    suspend fun initMovieName(name: String) {
        _detailState.value = MovieDetailState.Main(
            movieDetailEntity = getMovieDetailUseCase(movieName = name)
        )
    }

    override fun goBackToFeed() {
        TODO("Not yet implemented")
    }

    override fun openImdb() {
        TODO("Not yet implemented")
    }

    override fun onClickedRate() {
        TODO("Not yet implemented")
    }
}