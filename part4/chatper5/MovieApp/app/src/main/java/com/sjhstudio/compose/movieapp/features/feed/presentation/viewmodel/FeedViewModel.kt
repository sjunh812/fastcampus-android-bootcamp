package com.sjhstudio.compose.movieapp.features.feed.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sjhstudio.compose.movieapp.features.common.entity.EntityWrapper
import com.sjhstudio.compose.movieapp.features.feed.domain.usecase.IGetFeedCategoryUseCase
import com.sjhstudio.compose.movieapp.features.feed.presentation.input.IFeedViewModelInput
import com.sjhstudio.compose.movieapp.features.feed.presentation.output.FeedState
import com.sjhstudio.compose.movieapp.features.feed.presentation.output.FeedUiEffect
import com.sjhstudio.compose.movieapp.features.feed.presentation.output.IFeedViewModelOutput
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getFeedCategoryUseCase: IGetFeedCategoryUseCase
) : ViewModel(), IFeedViewModelInput, IFeedViewModelOutput {

    private val _feedState = MutableStateFlow<FeedState>(FeedState.Loading)
    override val feedState: StateFlow<FeedState>
        get() = _feedState

    private val _feedUiEffect = MutableSharedFlow<FeedUiEffect>()
    override val feedUiEffect: SharedFlow<FeedUiEffect>
        get() = _feedUiEffect

    init {
        fetchFeed()
    }

    private fun fetchFeed() {
        viewModelScope.launch {
            _feedState.value = FeedState.Loading

            val categories = getFeedCategoryUseCase()
            _feedState.value = when (categories) {
                is EntityWrapper.Success -> {
                    FeedState.Main(movieList = categories.entity)
                }

                is EntityWrapper.Fail -> {
                    FeedState.Failed(reason = categories.error.message ?: "UnKnown Error")
                }
            }
        }
    }

    override fun openDetail(movieName: String) {
        TODO("Not yet implemented")
    }

    override fun openInfoDialog() {
        TODO("Not yet implemented")
    }

    override fun refreshFeed() {
        TODO("Not yet implemented")
    }
}