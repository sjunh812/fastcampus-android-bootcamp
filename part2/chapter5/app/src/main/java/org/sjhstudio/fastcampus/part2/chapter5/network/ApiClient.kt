package org.sjhstudio.fastcampus.part2.chapter5.network

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit

object ApiClient {

    private const val BASE_URL = "https://news.google.com/"

    fun getRetrofit(): Retrofit {
        val tikXml = TikXml.Builder()
            .exceptionOnUnreadXml(false)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(TikXmlConverterFactory.create(tikXml))
            .build()
    }
}