package com.example.compose.exercise.network

import com.example.compose.exercise.data.model.Repo
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("users/{user}/repos")
    suspend fun listRepos(@Path("user") user: String): List<Repo>
}