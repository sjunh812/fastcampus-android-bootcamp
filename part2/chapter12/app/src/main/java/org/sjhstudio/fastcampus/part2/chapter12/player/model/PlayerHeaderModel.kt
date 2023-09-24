package org.sjhstudio.fastcampus.part2.chapter12.player.model

import org.sjhstudio.fastcampus.part2.chapter12.main.data.VideoEntity

data class PlayerHeaderModel(
    override val id: String,
    val description: String = "",
    val channelName: String = "",
    val viewCount: String = "",
    val dateText: String = "",
    val channelThumb: String = "",
    val title: String = ""
) : PlayerModel

fun VideoEntity.transformToPlayerHeader(): PlayerHeaderModel = PlayerHeaderModel(
    id = id,
    description = description,
    channelName = channelName,
    viewCount = viewCount,
    dateText = dateText,
    channelThumb = channelThumb,
    title = title
)

fun PlayerVideoModel.transformsToHeader(): PlayerHeaderModel = PlayerHeaderModel(
    id = id,
    description = description,
    channelName = channelName,
    viewCount = viewCount,
    dateText = dateText,
    channelThumb = channelThumb,
    title = title
)