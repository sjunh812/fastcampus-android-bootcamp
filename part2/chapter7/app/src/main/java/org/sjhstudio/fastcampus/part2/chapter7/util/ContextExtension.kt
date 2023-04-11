package org.sjhstudio.fastcampus.part2.chapter7.util

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.widget.RemoteViews
import android.widget.Toast
import org.sjhstudio.fastcampus.part2.chapter7.R
import org.sjhstudio.fastcampus.part2.chapter7.widget.WeatherAppWidgetProvider

fun Context.showToastMessage(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.updateRemoteViews(onUpdate: (RemoteViews) -> Unit) {
    val appWidgetManager = AppWidgetManager.getInstance(this)

    RemoteViews(packageName, R.layout.widget_weather)
        .apply {
            onUpdate(this)
        }.also { remoteViews ->
            val appWidgetName = ComponentName(this, WeatherAppWidgetProvider::class.java)
            appWidgetManager.updateAppWidget(appWidgetName, remoteViews)
        }
}