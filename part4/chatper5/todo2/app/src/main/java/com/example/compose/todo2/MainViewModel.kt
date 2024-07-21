package com.example.compose.todo2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _text = MutableLiveData<String>()
    val text: LiveData<String>
        get() = _text
    val setText: (String) -> Unit = { text -> _text.value = text }

    private val rawTodoList = mutableListOf<TodoData>()
    private val _todoList = MutableLiveData<List<TodoData>>()
    val todoList: LiveData<List<TodoData>>
        get() = _todoList

    val onSubmit: (String) -> Unit = { text ->
        val key = (rawTodoList.lastOrNull()?.key ?: 0).plus(1)
        rawTodoList.add(TodoData(key = key, text = text))
        _todoList.value = rawTodoList.toMutableList()
        setText("")
    }

    val onToggle: (Int, Boolean) -> Unit = { key, checked ->
        val i = rawTodoList.indexOfFirst { it.key == key }
        rawTodoList[i] = rawTodoList[i].copy(isDone = checked)
        _todoList.value = rawTodoList.toMutableList()
    }

    val onDelete: (Int) -> Unit = { key ->
        val i = rawTodoList.indexOfFirst { it.key == key }
        rawTodoList.removeAt(i)
        _todoList.value = rawTodoList.toMutableList()
    }

    val onEdit: (Int, String) -> Unit = { key, text ->
        val i = rawTodoList.indexOfFirst { it.key == key }
        rawTodoList[i] = rawTodoList[i].copy(text = text)
        _todoList.value = rawTodoList.toMutableList()
    }
}