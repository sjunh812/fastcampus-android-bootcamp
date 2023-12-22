package com.example.chapter4.mvi

sealed class MviIntent {
    data object LoadImage : MviIntent()
}