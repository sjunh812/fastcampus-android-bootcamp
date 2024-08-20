package com.example.compose.exercise

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.exercise.data.model.Repo
import com.example.compose.exercise.network.GithubService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    val githubService: GithubService
) : ViewModel() {

    val repos = mutableStateListOf<Repo>()

    fun getRepos(user: String = "sjunh812") {
        repos.clear()
        viewModelScope.launch {
            val result = githubService.listRepos(user)
            repos.addAll(result)
        }
    }
}