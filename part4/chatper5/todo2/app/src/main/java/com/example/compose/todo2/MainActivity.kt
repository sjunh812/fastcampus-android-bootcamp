package com.example.compose.todo2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.todo2.ui.theme.Todo2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            Todo2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TopLevel()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Todo2Theme {
        Greeting("Android")
    }
}

@Composable
fun TopLevel(viewModel: MainViewModel = viewModel()) {
    val text = viewModel.text.observeAsState("").value
    val todoList = viewModel.todoList.observeAsState(emptyList()).value

    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TodoInput(
                text = text,
                onTextChange = viewModel.setText,
                onSubmit = viewModel.onSubmit
            )
            LazyColumn {
                items(todoList, key = { it.key }) {
                    Todo(
                        todoData = it,
                        onToggle = viewModel.onToggle,
                        onDelete = viewModel.onDelete,
                        onEdit = viewModel.onEdit
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Todo2Theme {
        TopLevel()
    }
}

@Composable
fun TodoInput(
    text: String,
    onTextChange: (String) -> Unit,
    onSubmit: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.size(4.dp))
        Button(onClick = { onSubmit(text) }) {
            Text("입력")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoInputPreview() {
    Todo2Theme {
        TodoInput(text = "테스트", onTextChange = {}, onSubmit = {})
    }
}

@Composable
fun Todo(
    todoData: TodoData,
    onToggle: (key: Int, isChecked: Boolean) -> Unit = { _, _ -> },
    onDelete: (key: Int) -> Unit = { _ -> },
    onEdit: (key: Int, text: String) -> Unit = { _, _ -> }
) {
    // Mode
    var isEditing by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Crossfade<Boolean>(isEditing, label = "viewing or editor") {
            when (it) {
                true -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        val (text, setText) = remember { mutableStateOf(todoData.text) }

                        OutlinedTextField(
                            value = text,
                            onValueChange = setText,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Button(onClick = {
                            onEdit(todoData.key, text)
                            isEditing = false
                        }) {
                            Text("입력")
                        }
                    }
                }

                false -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(
                            text = todoData.text,
                            modifier = Modifier.weight(1f)
                        )
                        Text(text = "완료")
                        Checkbox(
                            checked = todoData.isDone,
                            onCheckedChange = { checked ->
                                onToggle(todoData.key, checked)
                            }
                        )
                        Button(onClick = { isEditing = true }) {
                            Text("수정")
                        }
                        Spacer(modifier = Modifier.size(4.dp))
                        Button(onClick = { onDelete(todoData.key) }) {
                            Text("삭제")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoPreview() {
    Todo2Theme {
        Todo(todoData = TodoData(0, "테스트"))
    }
}