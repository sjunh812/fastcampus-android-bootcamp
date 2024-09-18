package com.sjhstudio.compose.memo.ui.home

import android.content.Intent
import com.sjhstudio.compose.memo.ui.content.ContentActivity

class HomeState(val activity: HomeActivity) {

    fun showContent(index: Int) {
        activity.startActivity(Intent(activity, ContentActivity::class.java).apply {
            putExtra("id", index)
        })
    }
}