package com.example.chapter4.mvi

import com.example.chapter4.mvi.model.Image

sealed class MviState {
    data object Idle : MviState()
    data object Loading : MviState()
    data class LoadedImage(val image: Image, val count: Int) : MviState()
}