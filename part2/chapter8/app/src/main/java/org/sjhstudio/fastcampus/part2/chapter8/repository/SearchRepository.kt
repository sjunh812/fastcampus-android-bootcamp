package org.sjhstudio.fastcampus.part2.chapter8.repository

import org.sjhstudio.fastcampus.part2.chapter8.network.Network
import org.sjhstudio.fastcampus.part2.chapter8.network.SearchService
import org.sjhstudio.fastcampus.part2.chapter8.network.model.SearchResult
import retrofit2.Call

object SearchRepository {

    private val searchService = Network.getRetrofit().create(SearchService::class.java)

    fun getRestaurant(query: String): Call<SearchResult> {
        return searchService.getRestaurant("$query 맛집")
    }
}