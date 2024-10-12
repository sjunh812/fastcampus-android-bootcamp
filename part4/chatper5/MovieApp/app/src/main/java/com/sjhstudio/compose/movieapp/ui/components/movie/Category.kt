package com.sjhstudio.compose.movieapp.ui.components.movie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CategoryRow() {
    Column {
        CategoryTitle(title = "category title")
        LazyRow(
            contentPadding = PaddingValues(horizontal = 10.dp)
        ) {
            // Use itemsIndexed.
            item {
                MovieItem()
                MovieItem()
                MovieItem()
                MovieItem()
                MovieItem()
            }
        }
    }
}

@Composable
fun CategoryTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .padding(10.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun CategoryRawPreview() {
    CategoryRow()
}