package org.sjhstudio.chapter11.util

import android.content.Context
import com.google.gson.Gson
import org.sjhstudio.chapter11.model.Home
import java.io.IOException

fun Context.readData(): Home? {
    return try {
        val inputStream = resources.assets.open("home.json")
        val buffer = ByteArray(inputStream.available())

        inputStream.read(buffer)

        Gson().fromJson(String(buffer), Home::class.java)
    } catch (e: IOException) {
        null
    }
}