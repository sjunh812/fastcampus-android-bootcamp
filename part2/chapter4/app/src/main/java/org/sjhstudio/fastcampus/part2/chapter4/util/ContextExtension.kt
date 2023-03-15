package org.sjhstudio.fastcampus.part2.chapter4.util

import android.content.Context
import org.json.JSONObject

fun Context.findLanguageColor(languageName: String): String? {
    val jsonText = assets.open("language_colors.json").reader().readText()
    val jsonObject = JSONObject(jsonText)
    val languageObject = jsonObject.takeIf { jo ->
        jo.has(languageName)
    }?.getJSONObject(languageName)

    return languageObject?.getString("color")
}