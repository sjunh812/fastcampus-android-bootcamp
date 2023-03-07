package org.sjhstudio.fastcampus.part2.chapter1.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.sjhstudio.fastcampus.part2.chapter1.R
import org.sjhstudio.fastcampus.part2.chapter1.databinding.ActivityMainBinding
import org.sjhstudio.fastcampus.part2.chapter1.ui.adapter.WebtoonFragmentAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        const val SHARED_PREFERENCES = "web_history"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        with(binding) {
            viewPager.adapter = WebtoonFragmentAdapter(this@MainActivity)

            TabLayoutMediator(
                tabLayout,
                viewPager
            ) { tab, position ->
                val tabTextView = TextView(this@MainActivity)
                    .apply {
                        text = getTabName(position)
                        gravity = Gravity.CENTER
                        setTextColor(
                            ContextCompat.getColor(
                                this@MainActivity,
                                if (position != 0) R.color.gray
                                else R.color.purple_500
                            )
                        )
                    }
                tab.customView = tabTextView
            }.attach()

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    (tab?.customView as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            this@MainActivity,
                            R.color.purple_500
                        )
                    )
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    (tab?.customView as? TextView)?.setTextColor(
                        ContextCompat.getColor(
                            this@MainActivity,
                            R.color.gray
                        )
                    )
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }

    private fun getTabName(position: Int): String {
        val defaultName = when (position) {
            0 -> "월요웹툰"
            1 -> "화요웹툰"
            else -> "수요웹툰"
        }

        return getSharedPreferences(
            SHARED_PREFERENCES,
            MODE_PRIVATE
        )?.getString("tab${position}_name", "").takeIf { !it.isNullOrEmpty() } ?: defaultName
    }

    fun setTabName(position: Int, name: String) {
        binding.tabLayout.getTabAt(position)
            ?.let { tab -> (tab.customView as? TextView)?.text = name }
    }
}