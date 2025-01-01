package com.sjhstudio.compose.movieapp.ui.components.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Scale
import com.sjhstudio.compose.movieapp.R
import com.sjhstudio.compose.movieapp.features.common.entity.MovieFeedItemEntity
import com.sjhstudio.compose.movieapp.features.feed.presentation.input.IFeedViewModelInput
import com.sjhstudio.compose.movieapp.ui.theme.Paddings

private val CARD_WIDTH = 150.dp
private val CARD_HEIGHT = 200.dp
private val ICON_SIZE = 12.dp

@Composable
fun MovieItem(
    movie: MovieFeedItemEntity,
    input: IFeedViewModelInput
) {
    Column(
        modifier = Modifier
            .width(CARD_WIDTH)
            .padding(Paddings.large)
    ) {
        // Movie Poster
        Poster(
            movie = movie,
            input = input
        )
        // Title
        Text(
            text = movie.title,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = Paddings.medium)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = Paddings.small)
        ) {
            // Rating icon
            Icon(
                modifier = Modifier
                    .padding(Paddings.small)
                    .size(ICON_SIZE),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_rating),
                tint = Color.Black.copy(alpha = 0.5f),
                contentDescription = "rating icon"
            )
            // Rating
            Text(
                text = movie.rating.toString(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.5f
                )
            )
        }
    }
}

@Composable
fun Poster(
    movie: MovieFeedItemEntity,
    input: IFeedViewModelInput
) {
    Card(
        modifier = Modifier
            .size(CARD_WIDTH, CARD_HEIGHT),
        onClick = {
            input.openDetail(movie.title)
        }
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = rememberAsyncImagePainter(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(movie.thumbUrl)
                    .scale(Scale.FILL)
                    .apply {
                        crossfade(true)
                    }
                    .build()
            ),
            contentScale = ContentScale.FillHeight,
            contentDescription = "Movie Poster Image"
        )
    }
}

//@Preview
//@Composable
//fun MovieItemPreview() {
//    MovieAppTheme {
//        MovieItem()
//    }
//}