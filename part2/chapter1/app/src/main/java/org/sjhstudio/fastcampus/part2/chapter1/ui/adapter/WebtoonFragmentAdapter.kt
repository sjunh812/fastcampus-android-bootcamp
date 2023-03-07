package org.sjhstudio.fastcampus.part2.chapter1.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.sjhstudio.fastcampus.part2.chapter1.ui.WebtoonFragment

class WebtoonFragmentAdapter(private val fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        val url = getLastWebtoonHistoryOrDefault(position)
        return when (position) {
            0 -> WebtoonFragment(position, url)
            1 -> WebtoonFragment(position, url)
            else -> WebtoonFragment(position, url)
        }
    }

    private fun getLastWebtoonHistoryOrDefault(position: Int): String {
        return when (position) {
            0 -> "https://comic.naver.com/webtoon?tab=mon"
            1 -> "https://comic.naver.com/webtoon?tab=tue"
            else -> "https://comic.naver.com/webtoon?tab=wed"
        }
//        return fragmentActivity.getSharedPreferences(
//            MainActivity.SHARED_PREFERENCES,
//            Context.MODE_PRIVATE
//        ).getString("tab$position", "").takeIf { !it.isNullOrEmpty() } ?: defaultUrl
    }
}