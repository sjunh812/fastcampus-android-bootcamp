package com.sjhstudio.compose.movieapp.features.feed.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sjhstudio.compose.movieapp.R
import com.sjhstudio.compose.movieapp.features.feed.presentation.input.IFeedViewModelInput
import com.sjhstudio.compose.movieapp.features.feed.presentation.output.FeedState
import com.sjhstudio.compose.movieapp.ui.components.movie.CategoryRow
import com.sjhstudio.compose.movieapp.ui.theme.Paddings
import timber.log.Timber

val HEIGHT_TOP_APP_BAR = 70.dp
val PADDING_COMMON_HORIZONTAL = Paddings.medium

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    feedStateHolder: State<FeedState>,
    input: IFeedViewModelInput,
//    buttonColor: State<Color>,
//    changeAppColor: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .requiredHeight(HEIGHT_TOP_APP_BAR),
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    scrolledContainerColor = MaterialTheme.colorScheme.secondary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                title = {
                    Text(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(Paddings.medium)
                            .wrapContentHeight(align = Alignment.CenterVertically),
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.headlineMedium,
                    )
                },
                actions = {
//                    AppBarMenu(
//                        buttonColor = buttonColor.value,
//                        changeAppColor = changeAppColor,
//                        input = input
//                    )
                },
                navigationIcon = {
                    Box(modifier = Modifier.size(0.dp)) {

                    }
                }
            )
        },
    ) { contentPadding ->
        BodyContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            feedState = feedStateHolder.value,
            input = input
        )
    }
}

private val SIZE_APP_COLOR_BUTTON = 40.dp

@Composable
fun AppBarMenu(
    buttonColor: Color,
    changeAppColor: () -> Unit,
    input: IFeedViewModelInput
) {
    Row(
        modifier = Modifier
            .padding(end = PADDING_COMMON_HORIZONTAL)
    ) {
        IconButton(
            onClick = {
                changeAppColor()
            }
        ) {
            Box(
                modifier = Modifier
                    .size(SIZE_APP_COLOR_BUTTON)
                    .clip(CircleShape)
                    .background(buttonColor)
            )
        }
        IconButton(
            onClick = {
                input.openInfoDialog()
            }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_information),
                contentDescription = "Information",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun BodyContent(
    modifier: Modifier,
    feedState: FeedState,
    input: IFeedViewModelInput
) {
    when (feedState) {
        is FeedState.Loading -> {
            Timber.d("MoviesScreen: Loading")
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }

        is FeedState.Main -> {
            Timber.d("MoviesScreen: Success")
            LazyColumn(modifier = modifier) {
                itemsIndexed(items = feedState.movieList) { _, category ->
                    CategoryRow(
                        categoryEntity = category,
                        input = input
                    )
                }
            }
        }

        is FeedState.Failed -> {
            Timber.d("MoviesScreen: Failed")
            RetryMessage(
                message = feedState.reason,
                input = input
            )
        }
    }
}

private val SIZE_RETRY_IMAGE = 48.dp

@Composable
fun RetryMessage(
    modifier: Modifier = Modifier,
    message: String,
    input: IFeedViewModelInput
    /*onRetryClicked: () -> Unit = {}*/
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .requiredSize(SIZE_RETRY_IMAGE),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_warning),
            contentDescription = null
        )
        Text(
            text = message,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(
                    top = Paddings.xlarge,
                    bottom = Paddings.extra
                )
        )
        Button(
            onClick = {
                input.refreshFeed()
            }
        ) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}