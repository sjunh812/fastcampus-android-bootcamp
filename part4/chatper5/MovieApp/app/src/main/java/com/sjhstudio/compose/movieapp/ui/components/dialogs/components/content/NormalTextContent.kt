package com.sjhstudio.compose.movieapp.ui.components.dialogs.components.content

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.sjhstudio.compose.movieapp.ui.models.DialogText

@Composable
fun ColumnScope.NormalDialogTextContent(text: DialogText) {
    Text(
        text = text.id?.let { stringResource(id = it) } ?: text.text.orEmpty(),
        modifier = Modifier
            .padding(
                top = LocalDialogContentStyle.current.contentTopPadding,
                bottom = LocalDialogContentStyle.current.contentBottomPadding
            )
            .align(Alignment.CenterHorizontally),
        textAlign = TextAlign.Center,
        style = LocalDialogContentStyle.current.textStyle.invoke()
    )
}