package com.sjhstudio.compose.movieapp.features.feed.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sjhstudio.compose.movieapp.features.common.repository.IMovieDataSource
import com.sjhstudio.compose.movieapp.library.network.model.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val repository: IMovieDataSource    // test
) : ViewModel() {

    fun test() {
        viewModelScope.launch {
            val result = repository.getMovies()
            when (result.response) {
                is ApiResponse.Success -> {
                    Timber.d("${result.response.data}")
                }

                is ApiResponse.Fail -> {
                    Timber.e(result.response.error)
                }
            }
        }
    }
}