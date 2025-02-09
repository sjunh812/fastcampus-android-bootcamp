package com.sjhstudio.compose.movieapp.features.detail.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.launch

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
        viewModelScope.launch {
            _detailUiEffect.emit(MovieDetailUiEffect.Back)
        }
    }

    override fun openImdb() {
        viewModelScope.launch {
            if (detailState.value is MovieDetailState.Main) {
                _detailUiEffect.emit(MovieDetailUiEffect.OpenUrl(url = (detailState.value as MovieDetailState.Main).movieDetailEntity.imdbPath))
            }
        }
    }

    override fun onClickedRate() {
        viewModelScope.launch {
            if (detailState.value is MovieDetailState.Main) {
                val movieEntity = (detailState.value as MovieDetailState.Main).movieDetailEntity

                _detailUiEffect.emit(
                    MovieDetailUiEffect.OpenMovieRateDialog(
                        movieTitle = movieEntity.title,
                        rating = movieEntity.rating
                    )
                )
            }
        }
    }
}