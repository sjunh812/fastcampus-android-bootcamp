package com.sjhstudio.compose.movieapp.ui.components.dialogs

import androidx.compose.runtime.Composable
import com.sjhstudio.compose.movieapp.ui.models.DialogButton
import com.sjhstudio.compose.movieapp.ui.models.DialogContent
import com.sjhstudio.compose.movieapp.ui.models.DialogText
import com.sjhstudio.compose.movieapp.ui.models.DialogTitle

object DialogPopup

@Composable
fun DialogPopup.Default(
    title: String,
    bodyText: String,
    buttons: List<DialogButton>
) {
    BaseDialogPopup(
        dialogTitle = DialogTitle.Default(title),
        dialogContent = DialogContent.Default(
            DialogText.Default(text = bodyText)
        ),
        dialogButtons = buttons
    )
}