package com.example.part3.chapter5

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.part3.chapter5.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val searchFragment: SearchFragment = SearchFragment()
    private val fragmentList: List<Fragment> = listOf(searchFragment, FavoritesFragment())
    private val viewPagerAdapter: ViewPagerAdapter = ViewPagerAdapter(
        fragmentManager = supportFragmentManager,
        lifecycle = lifecycle,
        list = fragmentList
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
            it.view = this
        }
        initViews()
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
        (menu.findItem(R.id.search).actionView as? SearchView)?.run {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
        return true
    }
}