package com.example.chapter4.mvp.repository

import android.util.Log
import com.example.chapter4.ImageResponse
import com.example.chapter4.RetrofitManager
import retrofit2.Call
import retrofit2.Response

class ImageRepositoryImpl : ImageRepository {

    override fun getRandomImage(callback: ImageRepository.Callback) {
        RetrofitManager.imageService
            .getRandomImage()
            .enqueue(object : retrofit2.Callback<ImageResponse> {
                override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                    response.takeIf { it.isSuccessful }?.body()?.let {
                        callback.loadImage(it.urls.regular, it.color)
                    }
                }

                override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                    Log.e("SJH", "getRandomImage :: error(${t.message}")
                }
            })
    }
}