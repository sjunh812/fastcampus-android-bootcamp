package com.sjhstudio.compose.movieapp.ui.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.sjhstudio.compose.movieapp.ui.components.dialogs.components.button.DialogButtonsColumn
import com.sjhstudio.compose.movieapp.ui.components.dialogs.components.content.DialogContentWrapper
import com.sjhstudio.compose.movieapp.ui.components.dialogs.components.title.DialogTitleWrapper
import com.sjhstudio.compose.movieapp.ui.models.DialogButton
import com.sjhstudio.compose.movieapp.ui.models.DialogContent
import com.sjhstudio.compose.movieapp.ui.models.DialogText
import com.sjhstudio.compose.movieapp.ui.models.DialogTitle
import com.sjhstudio.compose.movieapp.ui.theme.MovieAppTheme
import com.sjhstudio.compose.movieapp.ui.theme.Paddings

@Composable
fun BaseDialogPopup(
    dialogTitle: DialogTitle? = null,
    dialogContent: DialogContent? = null,
    dialogButtons: List<DialogButton>? = null
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = Paddings.none),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            dialogTitle?.let { DialogTitleWrapper(title = it) }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Transparent)
                    .padding(
                        start = Paddings.xlarge,
                        end = Paddings.xlarge,
                        bottom = Paddings.xlarge
                    )
            ) {
                dialogContent?.let { DialogContentWrapper(content = it) }
                dialogButtons?.let { DialogButtonsColumn(buttons = it) }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun BaseDialogPreview() {
    MovieAppTheme {
        BaseDialogPopup(
            dialogTitle = DialogTitle.Header(title = "헤더 제목"),
            dialogContent = DialogContent.Large(dialogText = DialogText.Default(text = "동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라 만세")),
            dialogButtons = listOf(
                DialogButton.Primary(title = "확인"),
                DialogButton.Secondary(title = "취소")
            )
        )
    }
}

@Preview(showBackground = false)
@Composable
fun BaseDialogPreview2() {
    MovieAppTheme {
        BaseDialogPopup(
            dialogTitle = DialogTitle.Large(title = "헤더 제목"),
            dialogContent = DialogContent.Default(dialogText = DialogText.Default(text = "동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리나라 만세")),
            dialogButtons = listOf(
                DialogButton.Secondary(title = "확인"),
                DialogButton.UnderlinedText(title = "취소")
            )
        )
    }
}

@Preview(showBackground = false)
@Composable
fun BaseDialogPreview3() {
    MovieAppTheme {
        BaseDialogPopup(
            dialogTitle = DialogTitle.Large(title = "헤더 제목"),
            dialogContent = DialogContent.Rating(
                dialogText = DialogText.Rating(
                    text = "평점",
                    rating = 8.5F
                )
            ),
            dialogButtons = listOf(
                DialogButton.Primary(title = "확인"),
                DialogButton.Secondary(title = "취소")
            )
        )
    }
}