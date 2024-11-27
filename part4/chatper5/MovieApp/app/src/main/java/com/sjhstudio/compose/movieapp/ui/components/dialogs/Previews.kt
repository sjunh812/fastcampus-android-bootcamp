package com.sjhstudio.compose.movieapp.ui.components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.sjhstudio.compose.movieapp.R
import com.sjhstudio.compose.movieapp.ui.models.DialogButton
import com.sjhstudio.compose.movieapp.ui.models.buttons.LeadingIconData
import com.sjhstudio.compose.movieapp.ui.theme.MovieAppTheme

@Preview(showBackground = false)
@Composable
fun AlertPreview() {
    MovieAppTheme {
        DialogPopup.Alert(
            title = stringResource(R.string.app_name),
            bodyText = "This App is to build beautiful structure of Android app which uses Jetpack Compose.",
            buttons = listOf(
                DialogButton.UnderlinedText(stringResource(R.string.btn_close))
            )
        )
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MovieAppTheme {
        DialogPopup.Default(
            title = "Open IMDB",
            bodyText = "This will open the IMDB page in the external web browser. Are you okay?",
            buttons = listOf(
                DialogButton.Primary(title = stringResource(R.string.btn_open)),
                DialogButton.SecondaryBorderless(title = stringResource(R.string.btn_cancel))
            )
        )
    }
}

@Preview
@Composable
fun RatingPreview() {
    MovieAppTheme {
        DialogPopup.Rating(
            title = stringResource(R.string.title_dialog_popup_rating),
            bodyText = "The Lord of the Rings: The Two Towers",
            rating = 7.5F,
            buttons = listOf(
                DialogButton.Primary(
                    title = stringResource(R.string.btn_submit),
                    leadingIconData = LeadingIconData(
                        iconDrawable = R.drawable.ic_send,
                        iconContentDescription = R.string.description_btn_send
                    )
                ),
                DialogButton.Secondary(
                    title = stringResource(R.string.btn_cancel)
                )
            )
        )
    }
}