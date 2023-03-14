package org.sjhstudio.fastcampus.part2.chapter4.network

import org.sjhstudio.fastcampus.part2.chapter4.model.Repo
import org.sjhstudio.fastcampus.part2.chapter4.model.UsersDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

//    @Headers("Authorization: Bearer <Token>")
    @GET("/users/{username}/repos")
    fun listRepos(@Path("username") username: String): Call<List<Repo>>

    @GET("/search/users")
    fun searchUsers(@Query("q") query: String): Call<UsersDto>
}