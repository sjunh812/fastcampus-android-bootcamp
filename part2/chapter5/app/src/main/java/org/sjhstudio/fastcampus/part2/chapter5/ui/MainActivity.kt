package org.sjhstudio.fastcampus.part2.chapter5.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import org.sjhstudio.fastcampus.part2.chapter5.R
import org.sjhstudio.fastcampus.part2.chapter5.databinding.ActivityMainBinding
import org.sjhstudio.fastcampus.part2.chapter5.model.NewsRss
import org.sjhstudio.fastcampus.part2.chapter5.model.NewsService
import org.sjhstudio.fastcampus.part2.chapter5.network.ApiClient
import org.sjhstudio.fastcampus.part2.chapter5.ui.adapter.NewsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val newsAdapter: NewsAdapter by lazy { NewsAdapter() }

    companion object {
        private const val LOG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        test()
    }

    private fun initViews() {
        with(binding) {
            rvNews.apply {
                adapter = newsAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    private fun test() {
        val newsService = ApiClient.getRetrofit().create(NewsService::class.java)

        newsService.mainFeed().enqueue(object : Callback<NewsRss> {
            override fun onFailure(call: Call<NewsRss>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<NewsRss>, response: Response<NewsRss>) {
                Log.d(LOG, "${response.body()?.channel?.items}")

                newsAdapter.submitList(response.body()?.channel?.items)
            }
        })
    }
}