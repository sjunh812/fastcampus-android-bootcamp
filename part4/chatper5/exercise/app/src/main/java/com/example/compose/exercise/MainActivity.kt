package com.example.compose.exercise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.exercise.ui.theme.ExerciseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExerciseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                }
            }
        }
    }
}

/* Exercise CompositionLocal */
@Composable
fun ExerciseCompositionLocal() {
    val LocalElevation = compositionLocalOf { 8.dp }    // custom composition local (with. elevation)
    CompositionLocalProvider(value = LocalElevation provides 12.dp) {
        Card(modifier = Modifier.padding(8.dp)) {
            Column(modifier = Modifier.padding(8.dp)) {
                CompositionLocalProvider(LocalContentColor provides Color.Black) {
                    Text("Hello, World!")
                    Text("Hello, World!")
                }
                // LocalContent Alpha don't exist in Material3
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExerciseCompositionLocalPreview() {
    ExerciseTheme {
        ExerciseCompositionLocal()
    }
}