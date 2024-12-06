package com.sjhstudio.compose.movieapp.library.network.retrofit

import java.util.Locale
import javax.inject.Inject
import okhttp3.Headers

class HeaderParser @Inject constructor() {
    fun parseHeadersToMap(header: Headers): Map<String, String> {
        val headerMap: MutableMap<String, String> = mutableMapOf()

        headerMap.iterator().forEach { (key, value) ->
            if (key.equals("set-cookie", ignoreCase = true)) {
                val keyInLowerCase = key.lowercase(Locale.getDefault())

                if (headerMap[keyInLowerCase].isNullOrEmpty()) {
                    headerMap[keyInLowerCase] = value
                } else {
                    headerMap[keyInLowerCase] = "${headerMap[key]}; $value"
                }
            } else {
                headerMap[key] = value
            }
        }

        return headerMap
    }
}