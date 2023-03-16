package org.sjhstudio.fastcampus.part2.chapter5.model

import retrofit2.Call
import retrofit2.http.GET

interface NewsService {

    @GET("/rss?hl=ko&gl=KR&ceid=KR:ko")
    fun mainFeed(): Call<NewsRss>
}