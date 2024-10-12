package com.sjhstudio.compose.memo.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sjhstudio.compose.memo.ui.model.Memo
import com.sjhstudio.compose.memo.ui.model.memos
import com.sjhstudio.compose.memo.ui.theme.MemoTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(homeState: HomeState) {
    MemoTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val memoList = remember { memos.sortedByDescending { it.id }.toMutableStateList() }
            val memoListState = rememberLazyListState()
            val coroutineScope = rememberCoroutineScope()
            val onClickAction: (Int) -> Unit = { id ->
                homeState.showContent(index = id)
            }

            Column {
                AddMemo(
                    memoList = memoList,
                    memoListState = memoListState,
                    scope = coroutineScope
                )
                MemoList(
                    onClickAction = onClickAction,
                    memoList = memoList,
                    memoListState = memoListState
                )
            }
        }
    }
}

@Composable
fun AddMemo(
    memoList: SnapshotStateList<Memo>,
    memoListState: LazyListState,
    scope: CoroutineScope
) {
    val inputValue = remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .padding(all = 16.dp)
            .height(200.dp)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            value = inputValue.value,
            onValueChange = { textFieldValue -> inputValue.value = textFieldValue }
        )
        Spacer(modifier = Modifier.size(8.dp))
        Button(
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentWidth(),
            shape = RoundedCornerShape(size = 8.dp),
            onClick = {
                scope.launch {
                    memoList.add(
                        index = 0,
                        Memo(id = memoList.size, text = inputValue.value)
                    )
                    memoListState.scrollToItem(0)
                    inputValue.value = ""
                }
            }
        ) {
            Text(text = "Add")
        }
    }
}

@Composable
fun ColumnScope.MemoList(
    onClickAction: (Int) -> Unit,
    memoList: SnapshotStateList<Memo>,
    memoListState: LazyListState
) {
    LazyColumn(
        modifier = Modifier.weight(1f),
        state = memoListState
    ) {
        items(
            items = memoList,
            key = { it.id }
        ) { memo ->
            Card(
                modifier = Modifier
                    .height(100.dp)
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                onClick = { onClickAction(memo.id) }
            ) {
                Text(
                    text = memo.text,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize()
                )
            }
        }
    }
}