package com.example.compose.component.components

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.Coil
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.compose.component.components.ui.theme.ComponentsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComponentsTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column {
                        ProfileCardEx(cardData = cardData)
                        ProfileCardEx(cardData = cardData)
                    }
                }
            }
        }
    }

    companion object {
        val cardData = CardData(
            imageUrl = "https://picsum.photos/200/300",
            imageDescription = "random image",
            author = "SJH",
            description = "This is profile card example."
        )
    }
}

@Composable
fun RowEx() {
    Row(
        modifier = Modifier
            .height(40.dp)
            .width(200.dp),
        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = "first",
            textAlign = TextAlign.Start,
            modifier = Modifier
                .weight(1f)
                .background(Color.DarkGray)
        )
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "추가",
            modifier = Modifier
                .weight(2f)
                .background(Color.Gray)
        )
        Text(
            text = "third",
            textAlign = TextAlign.End,
            modifier = Modifier
                .weight(1f)
                .background(Color.DarkGray)
//            modifier = Modifier.align(Alignment.Bottom)
        )
    }
}

@Composable
fun ColumnEx() {
    Column(
        modifier = Modifier.size(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(text = "1", modifier = Modifier.align(Alignment.Start))
        Text(text = "2")
        Text(text = "3", modifier = Modifier.align(Alignment.End))
    }
}

@Composable
fun Outer() {
    Column() {
        Inner(modifier = Modifier
            .widthIn(min = 50.dp, max = 100.dp)
            .height(300.dp))
        Inner()
    }
}

@Composable
fun Inner(modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier = modifier) {
        Text(text = "minWidth:$minWidth maxWidth:$maxWidth // minHeight:$minHeight maxHeight:$maxHeight")
        if (maxHeight > 150.dp) {
            Text(text = ":)", modifier = Modifier.align(Alignment.BottomCenter))
        }
    }
}

@Composable
fun ImageEx() {
    Column {
        Image(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "icon launcher foreground")
        Image(imageVector = Icons.Filled.Settings, contentDescription = "settings")
    }
}

@Composable
fun CoilEx() {
    Column {
        // 1. Google Recommended (Deprecated)
        val painter = rememberImagePainter(data = "https://picsum.photos/200/300")
        Image(painter = painter, contentDescription = "random image 1")

        // 2. Coil Recommended
        AsyncImage(model = "https://picsum.photos/200/300", contentDescription = "random image 2")
    }
}

@Composable
fun ProfileCardEx(cardData: CardData) {
    val placeHolderColor = Color(0x33000000)
    Card(modifier = Modifier.padding(4.dp)) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = cardData.imageUrl,
                contentDescription = cardData.imageDescription,
                placeholder = ColorPainter(placeHolderColor),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Column {
                Text(text = cardData.author)
                Text(text = cardData.description)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComponentsTheme {
//        RowEx()
//        ColumnEx()
//        Outer()
//        ImageEx()
//        CoilEx()
    }
}

data class CardData(
    val imageUrl: String,
    val imageDescription: String,
    val author: String,
    val description: String
)