package com.sjhstudio.compose.memo.ui.content

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sjhstudio.compose.memo.ui.theme.MemoTheme

class ContentActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemoTheme {
                ContentScreen(memoId = intent.getIntExtra("id", 0))
            }
        }
    }
}