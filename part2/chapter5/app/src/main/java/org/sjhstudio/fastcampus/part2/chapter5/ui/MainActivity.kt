package org.sjhstudio.fastcampus.part2.chapter5.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.jsoup.Jsoup
import org.sjhstudio.fastcampus.part2.chapter5.databinding.ActivityMainBinding
import org.sjhstudio.fastcampus.part2.chapter5.model.*
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
        callMainFeed()
    }

    private fun initViews() {
        with(binding) {
            rvNews.apply {
                adapter = newsAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    private fun callMainFeed() {
        val newsService = ApiClient.getRetrofit().create(NewsService::class.java)

        newsService.mainFeed().enqueue(object : Callback<NewsRss> {
            override fun onFailure(call: Call<NewsRss>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<NewsRss>, response: Response<NewsRss>) {
                Log.d(LOG, "${response.body()?.channel?.items}")

                val newsList = response.body()?.channel?.items?.transform()

                newsAdapter.submitList(newsList)
                callNewsThumbnail(newsList)
            }
        })
    }

    private fun callNewsThumbnail(list: List<NewsModel>?) {
        Thread {
            try {
                list?.forEachIndexed { i, news ->
                    val jsoup = Jsoup.connect(news.link).get()
                    val elements = jsoup.select("meta[property^=og:]")
                    val ogImageNode = elements.find { element -> element.attr("property") == "og:image" }
                    val imageUrl = ogImageNode?.attr("content")

                    news.imageUrl = imageUrl

                    runOnUiThread { newsAdapter.notifyItemChanged(i) }
                }
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }.start()
    }
}