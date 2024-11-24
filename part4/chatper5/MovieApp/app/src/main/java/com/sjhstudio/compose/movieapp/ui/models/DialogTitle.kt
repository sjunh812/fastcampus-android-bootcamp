package com.sjhstudio.compose.movieapp.ui.models

sealed class DialogTitle(
    open val title: String
) {

    data class Default(override val title: String) : DialogTitle(title)

    data class Header(override val title: String) : DialogTitle(title)

    data class Large(override val title: String) : DialogTitle(title)
}