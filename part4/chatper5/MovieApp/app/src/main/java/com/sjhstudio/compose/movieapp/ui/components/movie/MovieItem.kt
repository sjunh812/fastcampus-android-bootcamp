package com.sjhstudio.compose.movieapp.ui.components.movie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sjhstudio.compose.movieapp.R

private val CARD_WIDTH = 150.dp
private val CARD_HEIGHT = 200.dp

@Composable
fun MovieItem() {
    Column(
        modifier = Modifier
            .width(CARD_WIDTH)
            .padding(10.dp)
    ) {
        // Movie Poster
        Poster()
        // Title
        Text(
            text = "title",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = 11.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 10.dp)
        ) {
            // Rating icon
            Icon(
                modifier = Modifier
                    .padding(4.dp)
                    .size(12.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_rating),
                tint = Color.Black.copy(alpha = 0.5f),
                contentDescription = "rating icon"
            )
            // Rating
            Text(
                text = "0.0"
            )
        }
    }
}

@Composable
fun Poster() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(CARD_HEIGHT)
    ) {
        Box(
            modifier = Modifier.background(Color.Gray)
        ) {

        }
    }
}

@Preview
@Composable
fun MovieItemPreview() {
    MovieItem()
}