package com.sjhstudio.compose.movieapp.ui.components.buttons

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sjhstudio.compose.movieapp.R
import com.sjhstudio.compose.movieapp.ui.models.buttons.LeadingIconData
import com.sjhstudio.compose.movieapp.ui.theme.MovieAppTheme
import com.sjhstudio.compose.movieapp.ui.theme.Paddings
import com.sjhstudio.compose.movieapp.ui.theme.button
import com.sjhstudio.compose.movieapp.ui.theme.color.disabledSecondary

val RADIUS_BUTTON = 8.dp
val SIZE_LEADING_ICON = 24.dp

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    @StringRes id: Int? = null,
    text: String = "",
    leadingIconData: LeadingIconData? = null,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(RADIUS_BUTTON),
        onClick = onClick,
        colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.disabledSecondary,
            disabledContentColor = MaterialTheme.colorScheme.onSecondary
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIconData?.let {
                Icon(
                    modifier = Modifier.size(SIZE_LEADING_ICON),
                    painter = painterResource(id = it.iconDrawable),
                    contentDescription = stringResource(id = it.iconContentDescription)
                )
                Spacer(modifier = Modifier.width(Paddings.xsmall))
            }

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
fun PrimaryButtonPreview() {
    MovieAppTheme {
        PrimaryButton(
            text = "test",
            leadingIconData = LeadingIconData(
                iconDrawable = R.drawable.ic_send,
                iconContentDescription = R.string.description_btn_send
            )
        ) {}
    }
}