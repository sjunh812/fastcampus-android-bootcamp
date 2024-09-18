package com.sjhstudio.compose.memo.ui.content

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.sjhstudio.compose.memo.R
import com.sjhstudio.compose.memo.ui.model.memos

private val MinTitleOffset = 20.dp
private val MaxTitleOffset = 100.dp
private val HzPadding = Modifier.padding(horizontal = 24.dp)

@Composable
fun ContentScreen(memoId: Int) {
    val memo = remember { memos.single { it.id == memoId } }

    Box(modifier = Modifier.fillMaxSize()) {
        val scroll = rememberScrollState(0)
        Body(scroll = scroll)
        Title(memoText = memo.text, scrollProvider = { scroll.value })
    }
}

@Composable
fun Body(scroll: ScrollState) {
    Column(modifier = Modifier.background(Color.White)) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(MaxTitleOffset)
        )
        Column(modifier = Modifier.verticalScroll(scroll)) {
            Surface(Modifier.fillMaxWidth()) {
                Column(Modifier.background(Color.White)) {
                    Spacer(modifier = Modifier.height(110.dp))
                    Text(
                        text = stringResource(id = R.string.detail_placeholder),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black,
                        modifier = HzPadding
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun Title(memoText: String, scrollProvider: () -> Int) {
    val maxOffset = with(LocalDensity.current) { MaxTitleOffset.toPx() }
    val minOffset = with(LocalDensity.current) { MinTitleOffset.toPx() }

    Column(
        modifier = Modifier
            .heightIn(min = MaxTitleOffset)
            .offset {
                val offset = (maxOffset - scrollProvider()).coerceAtLeast(minOffset)
                IntOffset(x = 0, y = offset.toInt())
            }
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Text(
            text = memoText,
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Black,
            modifier = HzPadding
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "MEMO",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = HzPadding
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}