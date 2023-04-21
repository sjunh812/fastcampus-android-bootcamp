package org.sjhstudio.fastcampus.part2.chapter8.model

import com.naver.maps.geometry.LatLng

data class Restaurant(
    val title: String,
    val link: String,
    val address: String,
    val category: String,
    val subCategory: List<String>,
    val latLng: LatLng
)