package com.example.chapter4.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.chapter4.mvi.repository.ImageRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MviViewModel(private val imageRepository: ImageRepository) : ViewModel() {

    private val channel = Channel<MviIntent>()

    private val _state = MutableStateFlow<MviState>(MviState.Idle)
    val state: StateFlow<MviState>
        get() = _state

    private var count = 0

    init {
        handleIntent()
    }

    fun sendIntent(intent: MviIntent) {
        viewModelScope.launch {
            channel.send(intent)
        }
    }

    private fun handleIntent() {
        viewModelScope.launch {
            channel.consumeAsFlow().collectLatest {
                when (it) {
                    MviIntent.LoadImage -> loadImage()
                }
            }
        }
    }

    private suspend fun loadImage() {
        _state.value = MviState.Loading
        val image = imageRepository.getRandomImage()
        count++
        _state.value = MviState.LoadedImage(image, count)
    }

    class MviViewModelFactory(private val imageRepository: ImageRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MviViewModel(imageRepository) as T
        }
    }
}