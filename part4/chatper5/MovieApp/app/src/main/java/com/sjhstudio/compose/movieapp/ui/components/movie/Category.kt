package com.sjhstudio.compose.movieapp.ui.components.movie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sjhstudio.compose.movieapp.features.common.entity.CategoryEntity
import com.sjhstudio.compose.movieapp.features.feed.presentation.input.IFeedViewModelInput
import com.sjhstudio.compose.movieapp.ui.theme.Paddings

@Composable
fun CategoryRow(
    categoryEntity: CategoryEntity,
    input: IFeedViewModelInput
) {
    Column {
        CategoryTitle(title = categoryEntity.genre)
        LazyRow(
            contentPadding = PaddingValues(horizontal = 10.dp)
        ) {
            itemsIndexed(items = categoryEntity.movieFeedEntities) { _, entity ->
                MovieItem(
                    movie = entity,
                    input = input
                )
            }
        }
    }
}

@Composable
fun CategoryTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier
            .padding(
                vertical = Paddings.large,
                horizontal = Paddings.extra
            )
    )
}

//@Preview(showBackground = true)
//@Composable
//fun CategoryRawPreview() {
//    MovieAppTheme {
//        CategoryRow()
//    }
//}