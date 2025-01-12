package com.sjhstudio.compose.movieapp.features.detail.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Scale
import com.sjhstudio.compose.movieapp.R
import com.sjhstudio.compose.movieapp.features.common.entity.MovieDetailEntity
import com.sjhstudio.compose.movieapp.features.detail.presentation.input.IMovieDetailViewModelInputs
import com.sjhstudio.compose.movieapp.features.detail.presentation.output.MovieDetailState
import com.sjhstudio.compose.movieapp.features.feed.presentation.screen.HEIGHT_TOP_APP_BAR
import com.sjhstudio.compose.movieapp.ui.components.buttons.PrimaryButton
import com.sjhstudio.compose.movieapp.ui.components.buttons.SecondaryButton
import com.sjhstudio.compose.movieapp.ui.components.movie.MovieDetailInfo
import com.sjhstudio.compose.movieapp.ui.theme.Paddings
import com.sjhstudio.compose.movieapp.ui.theme.color.defaultFont
import com.sjhstudio.compose.movieapp.ui.theme.color.descriptionFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    state: State<MovieDetailState>,
    inputs: IMovieDetailViewModelInputs
) {
    val movieDetail by state

    when (movieDetail) {
        MovieDetailState.Initial -> {
            // Nothing.
        }

        is MovieDetailState.Main -> {
            val scrollState = rememberScrollState()

            Scaffold(
                topBar = {
                    TopAppBar(
                        modifier = Modifier
                            .requiredHeight(HEIGHT_TOP_APP_BAR),
                        colors = TopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            titleContentColor = MaterialTheme.colorScheme.onBackground,
                            actionIconContentColor = MaterialTheme.colorScheme.onBackground,
                            scrolledContainerColor = MaterialTheme.colorScheme.background,
                            navigationIconContentColor = MaterialTheme.colorScheme.onBackground
                        ),
                        title = {},
                        navigationIcon = {
                            IconButton(
                                modifier = Modifier
                                    .fillMaxHeight(),
                                onClick = {
                                    inputs.goBackToFeed()
                                }
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_back),
                                    contentDescription = "뒤로 가기"
                                )
                            }
                        },
                        actions = {}
                    )
                }
            ) { contentPadding ->
                DetailBodyContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding)
                        .verticalScroll(state = scrollState),
                    entity = (movieDetail as MovieDetailState.Main).movieDetailEntity,
                    inputs = inputs
                )
            }
        }
    }
}

@Composable
fun DetailBodyContent(
    modifier: Modifier = Modifier,
    entity: MovieDetailEntity,
    inputs: IMovieDetailViewModelInputs
) {
    Column(
        modifier = modifier
            .padding(Paddings.extra)
    ) {
        MovieDetailInfos(entity)
        MovieDescription(
            title = entity.title,
            description = entity.desc
        )
        PrimaryButton(
            modifier = Modifier
                .padding(vertical = Paddings.large)
                .fillMaxWidth(),
            text = "Add Rating Score",
            onClick = {
                inputs.onClickedRate()
            }
        )
        SecondaryButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = "OPEN IMDB",
            onClick = {
                inputs.openImdb()
            }
        )
    }
}

private val WIDTH_MOVIE_DETAIL_POSTER = 180.dp
private val HEIGHT_MOVIE_DETAIL_POSTER = 250.dp

@Composable
fun MovieDetailInfos(entity: MovieDetailEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = Paddings.medium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // Poster
        Image(
            modifier = Modifier
                .width(WIDTH_MOVIE_DETAIL_POSTER)
                .height(HEIGHT_MOVIE_DETAIL_POSTER),
            painter = rememberAsyncImagePainter(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(entity.imageUrl)
                    .scale(Scale.FILL)
                    .apply {
                        crossfade(true)
                    }.build()
            ),
            contentDescription = "Movie Poster"
        )
        Spacer(
            modifier = Modifier
                .width(Paddings.xlarge)
        )
        Column {
            // Rating
            MovieDetailInfo(
                title = "Rating",
                contents = entity.rating.toString()
            )
            // Director
            MovieDetailInfo(
                title = "Director",
                contents = entity.directors.joinToString(separator = ", ")
            )
            // Starring
            MovieDetailInfo(
                title = "Starring",
                contents = entity.actors.joinToString(separator = ", ")
            )
            // Genre
            MovieDetailInfo(
                title = "Genre",
                contents = entity.genres.joinToString(separator = ", ")
            )
        }
    }
}

@Composable
fun MovieDescription(
    title: String,
    description: String
) {
    Text(
        modifier = Modifier
            .padding(vertical = Paddings.medium),
        text = title,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.defaultFont
    )
    Text(
        modifier = Modifier
            .padding(bottom = Paddings.medium),
        text = description,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.descriptionFont
    )
}