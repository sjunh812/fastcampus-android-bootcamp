package org.sjhstudio.flow.chapter10.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class ArticleViewModel(state: SavedStateHandle) : ViewModel() {

    val articleId: String = state["articleId"] ?: throw IllegalArgumentException("No article id")

    private var _isBookmark = MutableLiveData<Boolean>(
        state["isBookmark"] ?: throw IllegalArgumentException("No bookmark data")
    )
    val isBookmark: LiveData<Boolean>
        get() = _isBookmark

    fun updateBookmark() {
        _isBookmark.value = _isBookmark.value?.not()
    }
}