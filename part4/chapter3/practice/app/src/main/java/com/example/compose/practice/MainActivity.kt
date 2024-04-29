package com.example.compose.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.compose.practice.ui.theme.PracticeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    DropdownMenuEx()
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

@Composable
fun DialogEx() {
    var showDialog by remember { mutableStateOf(false) }
    var count by remember { mutableStateOf(0) }

    Column {
        Button(onClick = { showDialog = true }) {
            Text(text = "Open dialog")
        }
        Text(text = "count : $count")
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(onClick = {
                    count++
                    showDialog = false
                }) {
                    Text(text = "Add")
                }
            },
            dismissButton = {
                Button(onClick = {
                    showDialog = false
                }) {
                    Text(text = "Cancel")
                }
            },
            title = {
                Text("Notification")
            },
            text = {
                Text("When click the add button, then count is Added\nPlease click the button")
            }
        )
    }
}

@Composable
fun CustomDialogEx() {
    var showDialog by remember { mutableStateOf(false) }
    var count by remember { mutableStateOf(0) }

    Column {
        Button(onClick = { showDialog = true }) {
            Text(text = "Open dialog")
        }
        Text(text = "count : $count")
    }

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Surface {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = "Title")
                    Text(text = "Please click the buttons")
                    ConstraintLayout {
                        val (btnPlus, btnMinus, btnCancel) = createRefs()
                        Button(
                            onClick = { showDialog = false },
                            modifier = Modifier.constrainAs(btnCancel) {
                                end.linkTo(btnPlus.start, margin = 4.dp)
                                centerVerticallyTo(btnPlus)
                            }) {
                            Text(text = "Cancel")
                        }
                        Button(
                            onClick = {
                                count++
                                showDialog = false
                            },
                            modifier = Modifier.constrainAs(btnPlus) {
                                end.linkTo(btnMinus.start, margin = 4.dp)
                                centerVerticallyTo(btnMinus)
                            }
                        ) {
                            Text(text = "+")
                        }
                        Button(
                            onClick = {
                                count--
                                showDialog = false
                            },
                            modifier = Modifier.constrainAs(btnMinus) {
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                            }
                        ) {
                            Text(text = "-")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DropdownMenuEx() {
    var expandDropdownMenu by remember { mutableStateOf(false) }
    var counter by remember { mutableStateOf(0) }

    Column {
        Button(onClick = {
            expandDropdownMenu = true
        }) {
            Text("Open DropdownMenu")
        }
        Text("Counter : $counter")
    }

    DropdownMenu(
        expanded = expandDropdownMenu,
        onDismissRequest = {
            expandDropdownMenu = false
        }
    ) {
        DropdownMenuItem(text = { Text("plus") }, onClick = { counter++ })
        DropdownMenuItem(text = { Text("minus") }, onClick = { counter-- })
    }
}

@Composable
fun SnackbarEx() {
    var counter by remember { mutableStateOf(0) }
    val snackbarHostState = remember { SnackbarHostState() }
//    val scaffoldState = rememberScaff
//    Scaffold(snackbarHost = snackbarHostState) {
//
//    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PracticeTheme {
//        CanvasEx()
//        DialogEx()
//        CustomDialogEx()
        DropdownMenuEx()
    }
}