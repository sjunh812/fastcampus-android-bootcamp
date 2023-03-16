package org.sjhstudio.fastcampus.part2.chapter4.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.sjhstudio.fastcampus.part2.chapter4.databinding.ActivityMainBinding
import org.sjhstudio.fastcampus.part2.chapter4.model.User
import org.sjhstudio.fastcampus.part2.chapter4.model.UsersDto
import org.sjhstudio.fastcampus.part2.chapter4.network.GithubService
import org.sjhstudio.fastcampus.part2.chapter4.network.ApiClient
import org.sjhstudio.fastcampus.part2.chapter4.ui.adapter.UserAdapter
import org.sjhstudio.fastcampus.part2.chapter4.util.textChangedFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val userAdapter: UserAdapter by lazy { UserAdapter { user -> navigateToRepoActivity(user) } }

//    private var userNameInput = ""
//    private val searchUserRunnable: Runnable by lazy { Runnable { searchUser(userNameInput) } }
//    private val searchUserHandler: Handler by lazy { Handler(Looper.getMainLooper()) }

    companion object {
        private const val LOG = "MainActivity"
        const val USER_NAME = "UserName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        with(binding) {
            rvUser.apply {
                adapter = userAdapter
                layoutManager = LinearLayoutManager(context)
            }

            setSearchDebounce()
//            etSearch.addTextChangedListener { input ->
//                userNameInput = input.toString()
//                searchUserHandler.run {
//                    removeCallbacks(searchUserRunnable)
//                    postDelayed(searchUserRunnable, 300)
//                }
//            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun setSearchDebounce() = CoroutineScope(Dispatchers.IO).launch {
        binding.etSearch.textChangedFlow()
            .debounce(500)
            .filter { text -> !text.isNullOrEmpty() }
            .onEach { text -> searchUser(text.toString()) }
            .collect()
    }

    private fun searchUser(query: String) {
        val githubApi = ApiClient.getRetrofit().create(GithubService::class.java)

        githubApi.searchUsers(query).enqueue(object : Callback<UsersDto> {
            override fun onFailure(call: Call<UsersDto>, t: Throwable) {
                Log.e(LOG, t.message.toString())
            }

            override fun onResponse(call: Call<UsersDto>, response: Response<UsersDto>) {
                Log.d(LOG, "Search Users : ${response.body().toString()}")
                userAdapter.submitList(response.body()?.users)
            }
        })
    }

    private fun navigateToRepoActivity(user: User) {
        val intent = Intent(this, RepoActivity::class.java)
            .apply { putExtra(USER_NAME, user.name) }
        startActivity(intent)
    }
}