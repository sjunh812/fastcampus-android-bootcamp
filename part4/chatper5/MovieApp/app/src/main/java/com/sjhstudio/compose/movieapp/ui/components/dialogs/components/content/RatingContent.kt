package com.sjhstudio.compose.movieapp.ui.components.dialogs.components.content

import android.content.res.ColorStateList
import android.widget.RatingBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import com.sjhstudio.compose.movieapp.ui.models.DialogText
import com.sjhstudio.compose.movieapp.ui.theme.Paddings
import com.sjhstudio.compose.movieapp.util.getAnnotatedText

@Composable
fun ColumnScope.RatingContent(dialogText: DialogText.Rating) {
    Column(
        modifier = Modifier
            .padding(
                top = Paddings.large,
                bottom = Paddings.xlarge
            )
    ) {
        RatingTable(
            rating = dialogText.rating,
            movieTitle = dialogText.text.orEmpty()
        )
    }
}

@Composable
fun ColumnScope.RatingTable(rating: Float, movieTitle: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = movieTitle.getAnnotatedText(),
            style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.secondary),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(Paddings.large))
        StarRatingBar(score = rating)
    }
}

@Composable
fun ColumnScope.StarRatingBar(score: Float) {
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .padding(Paddings.medium)
            .align(Alignment.CenterHorizontally),
        elevation = CardDefaults.cardElevation(Paddings.none)
    ) {
        Box(contentAlignment = Alignment.Center) {
            val foregroundColor = MaterialTheme.colorScheme.primary.toArgb()
            val starBackgroundColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2F).toArgb()
            val ratingBackgroundColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.01F).toArgb()

            AndroidView(
                factory = {
                    RatingBar(it).apply {
                        max = 10
                        stepSize = 0.5F
                        rating = score
                        numStars = 5
                        progressTintList = ColorStateList.valueOf(foregroundColor)
                        progressBackgroundTintList = ColorStateList.valueOf(starBackgroundColor)
                        setBackgroundColor(ratingBackgroundColor)
                    }
                }
            )
        }
    }
}