package org.sjhstudio.chapter11.model

data class Menu(
    val drinks: List<MenuItem>,
    val foods: List<MenuItem>
)

data class MenuItem(
    val name: String,
    val imageUrl: String
)