package org.sjhstudio.fastcampus.part2.chapter8.network.model

import com.naver.maps.geometry.Tm128
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.sjhstudio.fastcampus.part2.chapter8.model.Restaurant

@JsonClass(generateAdapter = true)
data class SearchResult(
    @Json(name = "items") val items: List<SearchItem>,
)

@JsonClass(generateAdapter = true)
data class SearchItem(
    @Json(name = "title") val title: String,
    @Json(name = "link") val link: String,
    @Json(name = "category") val category: String,
    @Json(name = "address") val address: String,
    @Json(name = "roadAddress") val roadAddress: String,
    @Json(name = "mapx") val mapX: Int,
    @Json(name = "mapy") val mapY: Int
) {

    fun toRestaurant(): Restaurant {
        val splitCategory = category.split(">")
        val category = splitCategory.firstOrNull().orEmpty()
        val subCategory = if (splitCategory.size > 1) splitCategory[1].split(",") else emptyList()

        return Restaurant(
            title = title,
            link = link,
            address = address,
            category = category,
            subCategory = subCategory,
            latLng = Tm128(mapX.toDouble(), mapY.toDouble()).toLatLng()
        )
    }
}