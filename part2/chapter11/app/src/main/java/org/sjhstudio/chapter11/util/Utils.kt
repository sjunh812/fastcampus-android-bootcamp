package org.sjhstudio.chapter11.util

import android.content.Context
import com.google.gson.Gson
import java.io.IOException

fun <T> Context.readData(fileName: String, classT: Class<T>): T? {
    return try {
        val inputStream = resources.assets.open(fileName)
        val buffer = ByteArray(inputStream.available())

        inputStream.read(buffer)

        Gson().fromJson(String(buffer), classT)
    } catch (e: IOException) {
        null
    }
}