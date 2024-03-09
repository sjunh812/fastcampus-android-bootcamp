package com.example.part3.chapter5

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.part3.chapter5.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val searchFragment: SearchFragment = SearchFragment()
    private val fragmentList: List<Fragment> = listOf(searchFragment, FavoritesFragment())
    private val viewPagerAdapter: ViewPagerAdapter = ViewPagerAdapter(
        fragmentManager = supportFragmentManager,
        lifecycle = lifecycle,
        list = fragmentList
    )

    private var observableTextQuery: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
            it.view = this
        }
        initViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        observableTextQuery?.dispose()
        observableTextQuery = null
    }

    private fun initViews() {
        with(binding) {
            vpSearch.adapter = viewPagerAdapter
            TabLayoutMediator(tlSearch, vpSearch) { tab, position ->
                tab.text = if (fragmentList[position] is SearchFragment) {
                    "검색 결과"
                } else {
                    "즐겨찾기"
                }
            }.attach()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)

        observableTextQuery = Observable.create { emitter: ObservableEmitter<String> ->
            (menu.findItem(R.id.search).actionView as? SearchView)?.run {
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        emitter.onNext(query)
                        return false
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        binding.vpSearch.setCurrentItem(0, false)
                        emitter.onNext(newText)
                        return false
                    }
                })
            }
        }.debounce(500, TimeUnit.MICROSECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                searchFragment.searchKeyword(it)
            }

        return true
    }
}