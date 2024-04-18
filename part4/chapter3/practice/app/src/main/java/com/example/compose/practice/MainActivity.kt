package com.example.compose.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.practice.ui.theme.PracticeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

                }
            }
        }
    }
}
@Composable
fun CanvasEx() {
    Canvas(modifier = Modifier.size(30.dp)) {
        drawLine(
            color = Color.Black,
            start = Offset(50f, 10f),
            end = Offset(60f, 30f)
        )

        drawCircle(
            color = Color.Green,
            radius = 10f,
            center = Offset(20f, 20f)
        )

        drawRect(
            color = Color.LightGray,
            topLeft = Offset(30f, 30f),
            size = Size(10f, 10f)
        )

        // Icons.Filled.Send
        //        moveTo(2.01f, 21.0f)
        //        lineTo(23.0f, 12.0f)
        //        lineTo(2.01f, 3.0f)
        //        lineTo(2.0f, 10.0f)
        //        lineToRelative(15.0f, 2.0f)
        //        lineToRelative(-15.0f, 2.0f)
        //        close()
        drawLine(color = Color.DarkGray, start = Offset(2.01f, 21.0f), end = Offset(23.0f, 12.0f))
        drawLine(color = Color.DarkGray, start = Offset(23.0f, 12.0f), end = Offset(2.01f, 3.0f))
        drawLine(color = Color.DarkGray, start = Offset(2.01f, 3.0f), end = Offset(2.0f, 10.0f))
        drawLine(color = Color.DarkGray, start = Offset(2.0f, 10.0f), end = Offset(17.0f, 12.0f))
        drawLine(color = Color.DarkGray, start = Offset(17f, 12.0f), end = Offset(2.0f, 14.0f))
        drawLine(color = Color.DarkGray, start = Offset(2.0f, 14.0f), end = Offset(2.01f, 21.0f))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PracticeTheme {
        CanvasEx()
    }
}