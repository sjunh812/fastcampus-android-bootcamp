package org.sjhstudio.fastcampus.part2.chapter4.model

import com.google.gson.annotations.SerializedName

data class UsersDto(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("items")
    val users: List<User>
)