package com.sjhstudio.compose.movieapp.ui.models

sealed class DialogContent {

    data class Default(val dialogText: DialogText.Default) : DialogContent()

    data class Large(val dialogText: DialogText.Default) : DialogContent()

    data class Rating(val dialogText: DialogText.Rating) : DialogContent()
}