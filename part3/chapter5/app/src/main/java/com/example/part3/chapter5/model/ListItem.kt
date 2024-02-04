package com.example.part3.chapter5.model

import java.util.Date

interface ListItem {
    val thumbnailUrl: String
    val dateTime: Date
    var isFavorite: Boolean

    override fun equals(other: Any?): Boolean
}