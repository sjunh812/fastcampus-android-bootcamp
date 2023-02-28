package org.sjhstudio.fastcampus.part1.chapter8.data

import android.net.Uri

sealed class ImageItem {
    data class Image(val uri: Uri) : ImageItem()
    object LoadMore : ImageItem()
}

