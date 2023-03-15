package org.sjhstudio.fastcampus.part2.chapter4.util

import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun EditText.textChangedFlow(): Flow<CharSequence?> = callbackFlow {
    addTextChangedListener { text -> trySend(text) }
    awaitClose { addTextChangedListener(null) }
}