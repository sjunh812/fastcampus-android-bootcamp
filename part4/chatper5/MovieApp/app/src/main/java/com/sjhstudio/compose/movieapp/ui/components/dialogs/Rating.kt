package com.sjhstudio.compose.movieapp.ui.components.dialogs

import androidx.compose.runtime.Composable
import com.sjhstudio.compose.movieapp.ui.models.DialogButton
import com.sjhstudio.compose.movieapp.ui.models.DialogContent
import com.sjhstudio.compose.movieapp.ui.models.DialogText
import com.sjhstudio.compose.movieapp.ui.models.DialogTitle

@Composable
fun DialogPopup.Rating(
    title: String,
    bodyText: String,
    rating: Float,
    buttons: List<DialogButton>
) {
    BaseDialogPopup(
        dialogTitle = DialogTitle.Large(title),
        dialogContent = DialogContent.Rating(
            DialogText.Rating(
                text = bodyText,
                rating = rating
            )
        ),
        dialogButtons = buttons
    )
}