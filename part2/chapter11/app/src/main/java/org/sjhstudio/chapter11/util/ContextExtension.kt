package org.sjhstudio.chapter11.util

import android.content.Context
import com.google.gson.Gson
import java.io.IOException

inline fun <reified T> Context.readData(fileName: String): T? {
    return try {
        val inputStream = resources.assets.open(fileName)
        val buffer = ByteArray(inputStream.available())

        inputStream.read(buffer)

        Gson().fromJson(String(buffer), T::class.java)
    } catch (e: IOException) {
        null
    }
}