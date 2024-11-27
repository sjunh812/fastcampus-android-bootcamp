package com.sjhstudio.compose.movieapp.ui.components.dialogs

import androidx.compose.runtime.Composable
import com.sjhstudio.compose.movieapp.ui.models.DialogButton
import com.sjhstudio.compose.movieapp.ui.models.DialogContent
import com.sjhstudio.compose.movieapp.ui.models.DialogText
import com.sjhstudio.compose.movieapp.ui.models.DialogTitle

@Composable
fun DialogPopup.Alert(
    title: String,
    bodyText: String,
    buttons: List<DialogButton>
) {
    BaseDialogPopup(
        dialogTitle = DialogTitle.Header(title),
        dialogContent = DialogContent.Large(
            DialogText.Default(text = bodyText)
        ),
        dialogButtons = buttons
    )
}