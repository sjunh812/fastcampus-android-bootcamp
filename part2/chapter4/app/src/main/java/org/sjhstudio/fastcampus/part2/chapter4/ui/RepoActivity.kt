package org.sjhstudio.fastcampus.part2.chapter4.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.sjhstudio.fastcampus.part2.chapter4.databinding.ActivityRepoBinding
import org.sjhstudio.fastcampus.part2.chapter4.model.Repo
import org.sjhstudio.fastcampus.part2.chapter4.network.ApiClient
import org.sjhstudio.fastcampus.part2.chapter4.network.GithubService
import org.sjhstudio.fastcampus.part2.chapter4.ui.MainActivity.Companion.USER_NAME
import org.sjhstudio.fastcampus.part2.chapter4.ui.adapter.RepoAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRepoBinding
    private val repoAdapter: RepoAdapter by lazy { RepoAdapter { repo -> openBrowser(repo) } }
    private var userName: String = ""
    private var reposPage: Int = 1

    companion object {
        private const val LOG = "RepoActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userName = intent.getStringExtra(USER_NAME) ?: return

        initViews()
        callRepoList(userName, reposPage)
    }

    private fun initViews() {
        with(binding) {
            tvUserName.text = userName

            rvRepo.apply {
                adapter = repoAdapter
                layoutManager = LinearLayoutManager(this@RepoActivity)

                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val lastPosition = (layoutManager as LinearLayoutManager).itemCount - 1
                        val lastVisiblePosition =
                            (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()

                        if (lastPosition <= lastVisiblePosition) {
                            callRepoList(userName, ++reposPage)
                        }
                    }
                })
            }
        }
    }

    private fun callRepoList(userName: String, page: Int) {
        val githubApi = ApiClient.getRetrofit().create(GithubService::class.java)
        githubApi.listRepos(userName, page).enqueue(object : Callback<List<Repo>> {
            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                Log.e(LOG, t.message.toString())
            }

            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                Log.d(LOG, "List Repos : ${response.body().toString()}")
                repoAdapter.submitList(repoAdapter.currentList + response.body().orEmpty())
            }
        })
    }

    private fun openBrowser(repo: Repo) =
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(repo.htmlUrl)))
}