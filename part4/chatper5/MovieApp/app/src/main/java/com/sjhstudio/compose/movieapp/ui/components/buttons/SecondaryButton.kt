package com.sjhstudio.compose.movieapp.ui.components.buttons

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sjhstudio.compose.movieapp.ui.theme.MovieAppTheme
import com.sjhstudio.compose.movieapp.ui.theme.Paddings
import com.sjhstudio.compose.movieapp.ui.theme.button
import com.sjhstudio.compose.movieapp.ui.theme.color.disabledSecondary

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    @StringRes id: Int? = null,
    text: String = "",
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(RADIUS_BUTTON),
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.secondary
        ),
        onClick = onClick,
        colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.secondary,
            disabledContainerColor = MaterialTheme.colorScheme.disabledSecondary,
            disabledContentColor = MaterialTheme.colorScheme.onSecondary
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = id?.let { stringResource(id = id) } ?: text,
                style = MaterialTheme.typography.button,
                modifier = Modifier.padding(Paddings.medium)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SecondaryButtonPreview() {
    MovieAppTheme {
        SecondaryButton(text = "test") {}
    }
}