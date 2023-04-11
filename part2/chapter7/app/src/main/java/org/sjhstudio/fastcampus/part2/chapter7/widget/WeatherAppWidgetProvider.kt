package org.sjhstudio.fastcampus.part2.chapter7.widget

import android.app.ForegroundServiceStartNotAllowedException
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import org.sjhstudio.fastcampus.part2.chapter7.R
import org.sjhstudio.fastcampus.part2.chapter7.service.WeatherWidgetService

// Broadcast Receiver
class WeatherAppWidgetProvider : AppWidgetProvider() {

    companion object {
        private const val LOG = "WeatherAppWidgetProvider"
        private const val REQUEST_WEATHER_WIDGET_SERVICE = 1
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        Log.e(LOG, "onUpdate()")

        appWidgetIds.forEach { appWidgetId ->
            val intent = Intent(context, WeatherWidgetService::class.java)
            val pendingIntent = PendingIntent.getService(
                context,
                REQUEST_WEATHER_WIDGET_SERVICE,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )

            RemoteViews(
                context.packageName,
                R.layout.widget_weather
            ).apply {
                setOnClickPendingIntent(R.id.container, pendingIntent)
            }.run {
                appWidgetManager.updateAppWidget(appWidgetId, this)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                try {
                    context.startForegroundService(
                        Intent(context, WeatherWidgetService::class.java)
                    )
                } catch (e: ForegroundServiceStartNotAllowedException) {
                    e.printStackTrace()
                }
            } else {
                context.startForegroundService(Intent(context, WeatherWidgetService::class.java))
            }
        }
    }
}
