package org.sjhstudio.fastcampus.part2.chapter4.network

import okhttp3.Interceptor
import okhttp3.Response
import org.sjhstudio.fastcampus.part2.chapter4.BuildConfig
import org.sjhstudio.fastcampus.part2.chapter4.network.ApiKey.GITHUB_API_ACCESS_TOKEN

class AppInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
        val newRequest = request().newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.GITHUB_API_ACCESS_TOKEN}")
            .build()
        proceed(newRequest)
    }
}