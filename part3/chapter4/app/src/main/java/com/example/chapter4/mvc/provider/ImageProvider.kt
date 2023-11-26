package com.example.chapter4.mvc.provider

import android.util.Log
import com.example.chapter4.ImageResponse
import com.example.chapter4.RetrofitManager
import retrofit2.Call
import retrofit2.Response

class ImageProvider(private val callback: Callback) {

    fun getRandomImage() {
        RetrofitManager.imageService
            .getRandomImage()
            .enqueue(object : retrofit2.Callback<ImageResponse> {
                override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                    response.takeIf { res -> res.isSuccessful }?.body()?.let {
                        callback.onLoadRandomImage(it.urls.regular, it.color)
                    }
                }

                override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                    Log.e("SJH", "getRandomImage :: error(${t.message}")
                }
            })
    }

    interface Callback {
        fun onLoadRandomImage(url: String, color: String)
    }
}