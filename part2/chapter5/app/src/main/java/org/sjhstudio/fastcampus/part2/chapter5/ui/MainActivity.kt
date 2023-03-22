package org.sjhstudio.fastcampus.part2.chapter5.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import org.jsoup.Jsoup
import org.sjhstudio.fastcampus.part2.chapter5.R
import org.sjhstudio.fastcampus.part2.chapter5.WebViewActivity
import org.sjhstudio.fastcampus.part2.chapter5.databinding.ActivityMainBinding
import org.sjhstudio.fastcampus.part2.chapter5.model.NewsModel
import org.sjhstudio.fastcampus.part2.chapter5.model.NewsRss
import org.sjhstudio.fastcampus.part2.chapter5.model.NewsService
import org.sjhstudio.fastcampus.part2.chapter5.model.transform
import org.sjhstudio.fastcampus.part2.chapter5.network.ApiClient
import org.sjhstudio.fastcampus.part2.chapter5.ui.adapter.NewsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter { url -> navigateToWebViewActivity(url) }
    }
    private val newsService: NewsService by lazy {
        ApiClient.getRetrofit().create(NewsService::class.java)
    }
    private val imm: InputMethodManager by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    companion object {
        private const val LOG = "MainActivity"
        const val NEWS_URL = "newsUrl"
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val view = currentFocus

        view?.takeIf { v -> v is TextInputEditText && ev.action == MotionEvent.ACTION_UP }
            ?.let { et ->
                val x = ev.rawX
                val y = ev.rawY
                val outLocation = IntArray(2)

                et.getLocationOnScreen(outLocation)

                if (x < outLocation[0] || x > outLocation[0] + et.width || y < outLocation[1] || y > outLocation[1] + et.height) {
                    et.clearFocus()
                    imm.hideSoftInputFromWindow(et.windowToken, 0)
                }
            }

        return super.dispatchTouchEvent(ev)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        newsService.mainFeed().submitList()
    }

    private fun initViews() {
        with(binding) {
            etSearch.apply {
                setOnEditorActionListener { v, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        chipGroup.clearCheck()
                        clearFocus()
                        imm.hideSoftInputFromWindow(v.windowToken, 0)
                        newsService.search(text.toString()).submitList()
                        true
                    } else {
                        false
                    }
                }
            }

            chipGroup.apply {
                setOnCheckedStateChangeListener { group, checkedIds ->
                    if (checkedIds.isEmpty()) return@setOnCheckedStateChangeListener
                    // singleSelection 이 반드시 true 여야 작동
                    Log.d(LOG, "checkedId: ${checkedIds.first()}")
                    etSearch.setText("")
//                    etSearch.clearFocus()

                    when (checkedIds.first()) {
                        R.id.chip_feed -> newsService.mainFeed().submitList()
                        R.id.chip_politics -> newsService.politicsNews().submitList()
                        R.id.chip_economy -> newsService.economyNews().submitList()
                        R.id.chip_society -> newsService.societyNews().submitList()
                        R.id.chip_it -> newsService.itNews().submitList()
                        R.id.chip_sport -> newsService.sportNews().submitList()
                    }
                }
            }

            rvNews.apply {
                adapter = newsAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    private fun Call<NewsRss>.submitList() {
        enqueue(object : Callback<NewsRss> {
            override fun onFailure(call: Call<NewsRss>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<NewsRss>, response: Response<NewsRss>) {
                Log.d(LOG, "${response.body()?.channel?.items}")

                val newsList = response.body()?.channel?.items?.transform()
                binding.viewSearchEmpty.isVisible = newsList.isNullOrEmpty()
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
                    val ogImageNode =
                        elements.find { element -> element.attr("property") == "og:image" }
                    val imageUrl = ogImageNode?.attr("content")

                    news.imageUrl = imageUrl

                    runOnUiThread { newsAdapter.notifyItemChanged(i) }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun navigateToWebViewActivity(url: String) {
        startActivity(
            Intent(this, WebViewActivity::class.java)
                .apply { putExtra(NEWS_URL, url) }
        )
    }
}