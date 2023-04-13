package org.sjhstudio.fastcampus.part2.chapter8.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResult(
    @Json(name = "items") val items: List<SearchItem>,
)

@JsonClass(generateAdapter = true)
data class SearchItem(
    @Json(name = "title") val title: String,
    @Json(name = "link") val link: String,
    @Json(name = "address") val address: String,
    @Json(name = "roadAddress") val roadAddress: String,
    @Json(name = "mapx") val mapX: Int,
    @Json(name = "mapy") val mapY: Int
)