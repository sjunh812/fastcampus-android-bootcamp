package org.sjhstudio.fastcampus.part2.chapter4

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.sjhstudio.fastcampus.part2.chapter4.databinding.ActivityMainBinding
import org.sjhstudio.fastcampus.part2.chapter4.model.Repo
import org.sjhstudio.fastcampus.part2.chapter4.model.UsersDto
import org.sjhstudio.fastcampus.part2.chapter4.network.GithubService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val BASE_URL = "https://api.github.com/"
        private const val LOG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val githubApi = retrofit.create(GithubService::class.java)

        githubApi.listRepos("sjunh812").enqueue(object : Callback<List<Repo>> {
            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                Log.e(LOG, t.message.toString())
            }

            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                Log.d(LOG, "List Repos : ${response.body().toString()}")
            }
        })

        githubApi.searchUsers("sjunh").enqueue(object : Callback<UsersDto> {
            override fun onFailure(call: Call<UsersDto>, t: Throwable) {
                Log.e(LOG, t.message.toString())
            }

            override fun onResponse(call: Call<UsersDto>, response: Response<UsersDto>) {
                Log.d(LOG, "Search Users : ${response.body().toString()}")
            }
        })
    }
}