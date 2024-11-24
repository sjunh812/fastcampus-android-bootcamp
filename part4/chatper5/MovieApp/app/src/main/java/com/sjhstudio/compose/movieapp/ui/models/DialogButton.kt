package com.sjhstudio.compose.movieapp.ui.models

import com.sjhstudio.compose.movieapp.ui.models.buttons.LeadingIconData

sealed class DialogButton(
    open val title: String,
    open val action: (() -> Unit)?
) {

    data class Primary(
        override val title: String,
        override val action: (() -> Unit)? = null,
        val leadingIconData: LeadingIconData? = null
    ) : DialogButton(title, action)

    data class Secondary(
        override val title: String,
        override val action: (() -> Unit)? = null
    ) : DialogButton(title, action)

    data class UnderlinedText(
        override val title: String,
        override val action: (() -> Unit)? = null
    ) : DialogButton(title, action)

    data class SecondaryBorderless(
        override val title: String,
        override val action: (() -> Unit)? = null
    ) : DialogButton(title, action)
}