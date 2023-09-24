package org.sjhstudio.fastcampus.part2.chapter12.main.data

import com.google.gson.annotations.SerializedName

data class VideoListEntity(
    @SerializedName("videos")
    val videos: List<VideoEntity> = emptyList()
)

data class VideoEntity(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("sources")
    val videoUrl: String = "",
    @SerializedName("channelName")
    val channelName: String = "",
    @SerializedName("viewCount")
    val viewCount: String = "",
    @SerializedName("dateText")
    val dateText: String = "",
    @SerializedName("channelThumb")
    val channelThumb: String = "",
    @SerializedName("thumb")
    val videoThumb: String = "",
    @SerializedName("title")
    val title: String = ""
)