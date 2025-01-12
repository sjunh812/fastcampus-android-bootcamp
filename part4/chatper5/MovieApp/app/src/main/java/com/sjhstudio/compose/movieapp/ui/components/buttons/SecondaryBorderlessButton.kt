package com.sjhstudio.compose.movieapp.ui.components.buttons

import androidx.annotation.StringRes
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
import com.sjhstudio.compose.movieapp.ui.theme.MovieAppTheme
import com.sjhstudio.compose.movieapp.ui.theme.Paddings
import com.sjhstudio.compose.movieapp.ui.theme.button
import com.sjhstudio.compose.movieapp.ui.theme.color.disabledSecondary

@Composable
fun SecondaryBorderlessButton(
    modifier: Modifier = Modifier,
    @StringRes id: Int? = null,
    text: String = "",
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(RADIUS_BUTTON),
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
                text = id?.let { stringResource(id = it) } ?: text,
                style = MaterialTheme.typography.button,
                modifier = Modifier.padding(Paddings.medium)
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun SecondaryBorderlessButtonPreview() {
    MovieAppTheme {
        SecondaryBorderlessButton(text = "test") {}
    }
}