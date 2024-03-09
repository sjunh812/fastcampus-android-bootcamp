package com.example.part3.chapter5.network

import com.example.part3.chapter5.BuildConfig
import com.example.part3.chapter5.model.ImageResponse
import com.example.part3.chapter5.model.VideoResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchService {

    @Headers("Authorization: KakaoAK ${BuildConfig.KAKAO_API_KEY}")
    @GET("image")
    fun searchImage(@Query("query") query: String): Observable<ImageResponse>

    @Headers("Authorization: KakaoAK ${BuildConfig.KAKAO_API_KEY}")
    @GET("vclip")
    fun searchVideo(@Query("query") query: String): Observable<VideoResponse>
}