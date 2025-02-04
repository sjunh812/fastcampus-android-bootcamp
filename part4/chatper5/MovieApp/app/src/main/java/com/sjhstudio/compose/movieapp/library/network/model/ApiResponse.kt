package com.sjhstudio.compose.movieapp.library.network.model

import com.google.gson.annotations.SerializedName

sealed class ApiResponse<out T> {
    data class Success<T>(
        @SerializedName("data")
        val data: T
    ) : ApiResponse<T>()

    data class Fail(
        @SerializedName("error")
        val error: Throwable
    ) : ApiResponse<Nothing>()
}