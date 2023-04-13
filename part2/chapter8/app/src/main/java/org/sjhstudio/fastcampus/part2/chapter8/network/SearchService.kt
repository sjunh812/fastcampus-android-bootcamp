package org.sjhstudio.fastcampus.part2.chapter8.network

import org.sjhstudio.fastcampus.part2.chapter8.network.model.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("/v1/search/local.json")
    fun getRestaurant(
        @Query("query") query: String,
        @Query("display") display: Int = 5,
        @Query("sort") sort: String = "comment"
    ): Call<SearchResult>
}