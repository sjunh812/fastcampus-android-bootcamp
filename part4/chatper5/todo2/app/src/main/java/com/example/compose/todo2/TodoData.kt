package com.example.compose.todo2

data class TodoData(
    val key: Int,
    val text: String,
    val isDone: Boolean = false
)