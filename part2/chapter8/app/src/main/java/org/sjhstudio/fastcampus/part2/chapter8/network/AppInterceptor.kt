package org.sjhstudio.fastcampus.part2.chapter8.network

import okhttp3.Interceptor
import okhttp3.Response
import org.sjhstudio.fastcampus.part2.chapter8.util.Constants.NAVER_CLIENT_ID
import org.sjhstudio.fastcampus.part2.chapter8.util.Constants.NAVER_CLIENT_SECRET

class AppInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
        val newRequest = request().newBuilder()
            .addHeader("X-Naver-Client-Id", NAVER_CLIENT_ID)
            .addHeader("X-Naver-Client-Secret", NAVER_CLIENT_SECRET)
            .build()

        proceed(newRequest)
    }
}