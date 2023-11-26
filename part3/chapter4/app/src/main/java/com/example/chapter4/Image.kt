package com.example.chapter4

import com.google.gson.annotations.SerializedName

/**
 * {
 *   "id": "Dwu85P9SOIk",
 *   "color": "#6E633A",
 *   "urls": {
 *     "raw": "https://images.unsplash.com/photo-1417325384643-aac51acc9e5d",
 *     "full": "https://images.unsplash.com/photo-1417325384643-aac51acc9e5d?q=75&fm=jpg",
 *     "regular": "https://images.unsplash.com/photo-1417325384643-aac51acc9e5d?q=75&fm=jpg&w=1080&fit=max",
 *     "small": "https://images.unsplash.com/photo-1417325384643-aac51acc9e5d?q=75&fm=jpg&w=400&fit=max",
 *     "thumb": "https://images.unsplash.com/photo-1417325384643-aac51acc9e5d?q=75&fm=jpg&w=200&fit=max"
 *   }
 * }
 */
data class ImageResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("color")
    val color: String,
    @SerializedName("urls")
    val urls: UrlsResponse
)

data class UrlsResponse(
    @SerializedName("row")
    val row: String,
    @SerializedName("full")
    val full: String,
    @SerializedName("regular")
    val regular: String,
    @SerializedName("small")
    val small: String,
    @SerializedName("thumb")
    val thumb: String
)