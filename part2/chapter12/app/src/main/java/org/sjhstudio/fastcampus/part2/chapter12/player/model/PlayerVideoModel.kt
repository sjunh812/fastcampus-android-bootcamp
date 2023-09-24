package org.sjhstudio.fastcampus.part2.chapter12.player.model

import org.sjhstudio.fastcampus.part2.chapter12.main.data.VideoEntity

data class PlayerVideoModel(
    override val id: String,
    val description: String = "",
    val videoUrl: String = "",
    val channelName: String = "",
    val viewCount: String = "",
    val dateText: String = "",
    val channelThumb: String = "",
    val videoThumb: String = "",
    val title: String = ""
) : PlayerModel

fun VideoEntity.transformToPlayerVideo(): PlayerVideoModel = PlayerVideoModel(
    id = id,
    description = description,
    videoUrl = videoUrl,
    channelName = channelName,
    viewCount = viewCount,
    dateText = dateText,
    channelThumb = channelThumb,
    videoThumb = videoThumb,
    title = title
)