package com.example.part3.chapter5.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class VideoResponse(
    val documents: List<VideoItem>
)

data class VideoItem(
    @SerializedName("thumbnail_url")
    override val thumbnailUrl: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("play_time")
    val playTime: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("datetime")
    override val dateTime: Date,
    override var isFavorite: Boolean = false
) : ListItem