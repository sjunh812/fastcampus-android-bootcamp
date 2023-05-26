package org.sjhstudio.flow.chapter10.ui.article

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WriteArticleViewModel : ViewModel() {

    private var _selectedUri = MutableLiveData<Uri?>()
    val selectedUri: LiveData<Uri?>
        get() = _selectedUri

    fun updateSelectedUri(uri: Uri?) {
        _selectedUri.value = uri
    }
}