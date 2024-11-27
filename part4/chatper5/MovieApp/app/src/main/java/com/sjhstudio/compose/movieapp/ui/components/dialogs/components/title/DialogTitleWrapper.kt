package com.sjhstudio.compose.movieapp.ui.components.dialogs.components.title

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.sjhstudio.compose.movieapp.ui.models.DialogTitle
import com.sjhstudio.compose.movieapp.ui.theme.Paddings
import com.sjhstudio.compose.movieapp.ui.theme.titleLarge24

data class DialogTitleStyle(
    val textStyle: @Composable () -> TextStyle = { MaterialTheme.typography.titleLarge },
    val contentPadding: Dp = Paddings.large
)

val LocalDialogTitleStyle = compositionLocalOf { DialogTitleStyle() }

@Composable
fun DialogTitleWrapper(title: DialogTitle) {
    when (title) {
        is DialogTitle.Default -> {
            CompositionLocalProvider(
                LocalDialogTitleStyle provides DialogTitleStyle(
                    textStyle = { MaterialTheme.typography.titleLarge },
                    contentPadding = Paddings.xlarge
                )
            ) {
                DefaultDialogTitle(title = title)
            }
        }

        is DialogTitle.Header -> {
            CompositionLocalProvider(
                LocalDialogTitleStyle provides DialogTitleStyle(
                    textStyle = { MaterialTheme.typography.titleLarge24 },
                    contentPadding = Paddings.xlarge
                )
            ) {
                HeaderDialogTitle(title = title)
            }
        }

        is DialogTitle.Large -> {
            CompositionLocalProvider(
                LocalDialogTitleStyle provides DialogTitleStyle(
                    textStyle = { MaterialTheme.typography.titleLarge24 },
                    contentPadding = Paddings.xlarge
                )
            ) {
                LargeDialogTitle(title = title)
            }
        }
    }
}


@Composable
fun DefaultDialogTitle(title: DialogTitle.Default) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onPrimary),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(LocalDialogTitleStyle.current.contentPadding),
            text = title.title,
            textAlign = TextAlign.Center,
            style = LocalDialogTitleStyle.current.textStyle.invoke().copy(color = MaterialTheme.colorScheme.secondary)
        )
    }
}

@Composable
fun LargeDialogTitle(title: DialogTitle.Large) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onPrimary),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(LocalDialogTitleStyle.current.contentPadding),
            text = title.title,
            textAlign = TextAlign.Center,
            style = LocalDialogTitleStyle.current.textStyle.invoke().copy(color = MaterialTheme.colorScheme.secondary)
        )
    }
}

@Composable
fun HeaderDialogTitle(title: DialogTitle.Header) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(LocalDialogTitleStyle.current.contentPadding),
            text = title.title,
            textAlign = TextAlign.Center,
            style = LocalDialogTitleStyle.current.textStyle.invoke().copy(color = MaterialTheme.colorScheme.onPrimary)
        )
    }
}
