package com.example.compose.constraintlayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.example.compose.constraintlayout.ui.theme.ConstraintlayoutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConstraintlayoutTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

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
fun ConstraintLayoutEx(cardData: CardData) {
    val constraintSet = ConstraintSet {
        val redBox = createRefFor("redBox")
        val magentaBox = createRefFor("magentaBox")
        val greenBox = createRefFor("greenBox")
        val text = createRefFor("text")

        createHorizontalChain(redBox, magentaBox, greenBox, chainStyle = ChainStyle.SpreadInside)
        val barrier = createBottomBarrier(redBox, magentaBox, greenBox)

        constrain(redBox) {
            top.linkTo(parent.top, margin = 10.dp)
        }

        constrain(magentaBox) {
            top.linkTo(parent.top, margin = 30.dp)
        }

        constrain(greenBox) {
            top.linkTo(parent.top, margin = 20.dp)
        }

        constrain(text) {
            top.linkTo(barrier)
        }

        // Profile Card Example
        val profileImage = createRefFor("profileImage")
        val author = createRefFor("author")
        val description = createRefFor("description")

        constrain(profileImage) {
            linkTo(top = text.bottom, bottom = parent.bottom)
        }

        constrain(author) {
            linkTo(
                start = profileImage.end,
                end = parent.end,
                startMargin = 8.dp,
                endMargin = 8.dp
            )
            width = Dimension.fillToConstraints
        }

        constrain(description) {
            linkTo(
                start = profileImage.end,
                end = parent.end,
                startMargin = 8.dp,
                endMargin = 8.dp
            )
            width = Dimension.fillToConstraints
        }

        val cardTextChain = createVerticalChain(author, description, chainStyle = ChainStyle.Packed)
        constrain(cardTextChain) {
            top.linkTo(profileImage.top)
            bottom.linkTo(profileImage.bottom)
        }
    }

    ConstraintLayout(constraintSet = constraintSet, modifier = Modifier.fillMaxSize()) {
//        val (redBox, magentaBox, greenBox, yellowBox) = createRefs()
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color = Color.Red)
                .layoutId("redBox")
//                .constrainAs(redBox) {
//                    bottom.linkTo(parent.bottom, margin = 8.dp)
//                    end.linkTo(parent.end, margin = 4.dp)
//                }
        )

        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color = Color.Magenta)
                .layoutId("magentaBox")
//                .constrainAs(magentaBox) {
//                    start.linkTo(parent.start)
//                    end.linkTo(parent.end)
//                }
        )

        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color = Color.Green)
                .layoutId("greenBox")
//                .constrainAs(greenBox) {
//                    centerTo(parent)
//                }
        )

        Text(
            text = "동해물과백두산이마르고닳도록하느님이보우하사우리나라만세무궁화삼천리화려강산대한사람대한으로길이보전하세",
            modifier = Modifier.layoutId("text")
        )

        val placeHolderColor = Color(0x33000000)
        AsyncImage(
            model = cardData.imageUrl,
            contentDescription = cardData.description,
            placeholder = ColorPainter(color = placeHolderColor),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .layoutId("profileImage")
                .size(40.dp)
                .clip(CircleShape)
        )

        Text(
            text = cardData.author,
            modifier = Modifier.layoutId("author")
        )

        Text(
            text = cardData.imageDescription,
            modifier = Modifier.layoutId("description")
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ConstraintlayoutTheme {
        ConstraintLayoutEx(cardData)
    }
}

val cardData = CardData(
    imageUrl = "https://picsum.photos/200/300",
    imageDescription = "random image",
    author = "SJH",
    description = "This is profile card example."
)

data class CardData(
    val imageUrl: String,
    val imageDescription: String,
    val author: String,
    val description: String
)