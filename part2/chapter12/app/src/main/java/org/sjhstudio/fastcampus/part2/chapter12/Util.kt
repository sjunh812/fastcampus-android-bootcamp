package org.sjhstudio.fastcampus.part2.chapter12

import android.content.Context
import com.google.gson.Gson
import java.io.IOException

inline fun <reified T> Context.readData(fileName: String): T? {
    return try {
        val inputStream = resources.assets.open(fileName)
        val byteArray = ByteArray(inputStream.available())

        inputStream.run {
            read(byteArray)
            close()
        }

        Gson().fromJson(String(byteArray), T::class.java)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}