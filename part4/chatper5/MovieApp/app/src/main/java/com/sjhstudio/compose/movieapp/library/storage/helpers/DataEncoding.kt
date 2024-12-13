package com.sjhstudio.compose.movieapp.library.storage.helpers

import android.util.Base64
import javax.inject.Inject

class DataEncoding @Inject constructor() {

    fun encodeToString(data: ByteArray?): String? {
        return Base64.encodeToString(data, Base64.NO_WRAP)
    }

    fun decode(data: String?): ByteArray? {
        return Base64.decode(data, Base64.NO_WRAP)
    }
}