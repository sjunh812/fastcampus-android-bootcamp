package com.sjhstudio.compose.movieapp.ui.components.movie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sjhstudio.compose.movieapp.ui.theme.MovieAppTheme
import com.sjhstudio.compose.movieapp.ui.theme.Paddings
import com.sjhstudio.compose.movieapp.ui.theme.color.defaultFont
import com.sjhstudio.compose.movieapp.ui.theme.color.descriptionFont

@Composable
fun MovieDetailInfo(
    title: String,
    contents: String
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.defaultFont.copy(alpha = 0.7F)
        )
        Spacer(
            modifier = Modifier
                .height(Paddings.small)
        )
        Text(
            text = contents,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.descriptionFont
        )
        Spacer(
            modifier = Modifier
                .height(Paddings.large)
        )
    }
}

@Preview(showBackground = false)
@Composable
fun MovieDetailInfoPreview() {
    MovieAppTheme {
        MovieDetailInfo(
            title = "타이틀",
            contents = "내용"
        )
    }
}