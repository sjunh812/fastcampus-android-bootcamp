package com.example.compose.todo

import android.annotation.SuppressLint
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.todo.ui.theme.TodoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoTheme {
                // A surface container using the 'background' color from the theme
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopLevel() {
    val (inputText, setInputText) = remember { mutableStateOf("") }   // getter, setter
    val todoList = remember { mutableStateListOf<TodoData>() }
    // Create
    val onSubmit: (String) -> Unit = { text ->
        val key = (todoList.lastOrNull()?.key ?: 0).plus(1)
        todoList.add(TodoData(key = key, text = text))
        setInputText("")
    }
    // Toggle
    val onToggle: (Int, Boolean) -> Unit = { key, checked ->
        val i = todoList.indexOfFirst { it.key == key }
        todoList[i] = todoList[i].copy(isDone = checked)
    }
    // Delete
    val onDelete: (Int) -> Unit = { key ->
        val i = todoList.indexOfFirst { it.key == key }
        todoList.removeAt(i)
    }
    // Edit
    val onEdit: (Int, String) -> Unit = { key, text ->
        val i = todoList.indexOfFirst { it.key == key }
        todoList[i] = todoList[i].copy(text = text)
    }

    Scaffold {
        Column {
            TodoInput(
                text = inputText,
                onTextChange = setInputText,
                onSubmit = onSubmit
            )
            LazyColumn {
                items(todoList, key = { it.key }) {
                    Todo(
                        todoData = it,
                        onToggle = onToggle,
                        onDelete = onDelete,
                        onEdit = onEdit
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TodoTheme {
        TopLevel()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
    TodoTheme {
        TodoInput(text = "테스트", onTextChange = {}, onSubmit = {})
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
        Crossfade(isEditing, label = "viewing or editor") {
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
    TodoTheme {
        Todo(todoData = TodoData(0, "테스트"))
    }
}

data class TodoData(
    val key: Int,
    val text: String,
    val isDone: Boolean = false
)