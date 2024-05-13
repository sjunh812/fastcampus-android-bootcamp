package com.example.compose.animation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.animation.ui.theme.AnimationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimationTheme {
//                AnimationEx()r
                Surface(modifier = Modifier.fillMaxSize()) {
                    AnimationEx2()
                }
            }
        }
    }
}

@Composable
fun AnimationEx() {
    var helloWorldVisible by remember { mutableStateOf(true) }
    var isRed by remember { mutableStateOf(false) }
    val animateColorState by animateColorAsState(targetValue = if (isRed) Color.Red else Color.White)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = animateColorState
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            AnimatedVisibility(
                visible = helloWorldVisible,
                enter = slideInVertically() + expandHorizontally(),
                exit = slideOutVertically() + shrinkHorizontally()
            ) {
                Text(
                    text = "Hello, World!",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Row(
                modifier = Modifier
                    .selectable(
                        selected = helloWorldVisible,
                        onClick = { helloWorldVisible = true }
                    )
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = helloWorldVisible, onClick = { helloWorldVisible = true })
                Text("show")
            }
            Row(
                modifier = Modifier
                    .selectable(
                        selected = helloWorldVisible.not(),
                        onClick = { helloWorldVisible = false },
                    )
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = helloWorldVisible.not(), onClick = { helloWorldVisible = false })
                Text("hide")
            }

            Spacer(modifier = Modifier.size(16.dp))

            Text(
                text = "Background Color",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
            Row(
                modifier = Modifier
                    .selectable(
                        selected = isRed,
                        onClick = { isRed = true }
                    )
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = isRed, onClick = { isRed = true })
                Text("red")
            }
            Row(
                modifier = Modifier
                    .selectable(
                        selected = isRed.not(),
                        onClick = { isRed = false },
                    )
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = isRed.not(), onClick = { isRed = false })
                Text("white")
            }
        }
    }
}

@Composable
fun AnimationEx2() {
    var isDarkMode by remember { mutableStateOf(false) }
    val darkModeTransition = updateTransition(targetState = isDarkMode, label = "Dark Mode Transition")
    val backgroundColor by darkModeTransition.animateColor(label = "Dark Mode Background Animation") { darkMode ->
        when (darkMode) {
            true -> Color.Black
            false -> Color.White
        }
    }
    val textColor by darkModeTransition.animateColor(label = "Dark Mode Text Color Animation") { darkMode ->
        when (darkMode) {
            true -> Color.White
            false -> Color.Black
        }
    }
    val alpha by darkModeTransition.animateFloat(label = "Dark Mode Alpha Animation") { darkMode ->
        when (darkMode) {
            true -> 1f
            false -> 0.5f
        }
    }

    Column(
        modifier = Modifier
            .background(color = backgroundColor)
            .alpha(alpha)
    ) {
        RadioButtonWithText(
            selected = isDarkMode.not(),
            text = "normal mode",
            textColor = textColor
        ) {
            isDarkMode = false
        }
        RadioButtonWithText(
            selected = isDarkMode,
            text = "dark mode",
            textColor = textColor
        ) {
            isDarkMode = true
        }

        Spacer(modifier = Modifier.size(16.dp))

        Crossfade(targetState = isDarkMode, label = "Dark Mode Cross fade") { state ->
            val boxList = when (state) {
                true -> listOf("다", "크", "모", "드")
                false -> listOf("일", "반", "모", "드")
            }

            Row {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(Color.Red)
                ) {
                    Text(boxList[0])
                }
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(Color.Blue)
                ) {
                    Text(boxList[1])
                }
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(Color.Green)
                ) {
                    Text(boxList[2])
                }
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(Color.Yellow)
                ) {
                    Text(boxList[3])
                }
            }
        }
    }
}

@Composable
fun RadioButtonWithText(
    selected: Boolean = false,
    text: String = "",
    textColor: Color = Color.Black,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .selectable(
                selected = selected,
                onClick = onClick
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = onClick)
        Text(text = text, color = textColor)
    }
}

@Preview(showBackground = true)
@Composable
fun RadioButtonWithTextPreview() {
    AnimationTheme {
        RadioButtonWithText(selected = true, text = "Example")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AnimationTheme {
        AnimationEx2()
    }
}