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
import org.sjhstudio.fastcampus.part2.chapter7.ui.view.MainActivity

// Broadcast Receiver
class WeatherAppWidgetProvider : AppWidgetProvider() {

    companion object {
        private const val LOG = "WeatherAppWidgetProvider"
        private const val REQUEST_WEATHER_WIDGET_SERVICE = 1
        private const val REQUEST_MAIN_ACTIVITY = 2
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        Log.e(LOG, "onUpdate()")

        appWidgetIds.forEach { appWidgetId ->
            val serviceIntent = Intent(context, WeatherWidgetService::class.java)
            val servicePendingIntent = PendingIntent.getService(
                context,
                REQUEST_WEATHER_WIDGET_SERVICE,
                serviceIntent,
                PendingIntent.FLAG_IMMUTABLE
            )
            val activityIntent = Intent(context, MainActivity::class.java)
                .apply { flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP }
            val activityPendingIntent = PendingIntent.getActivity(
                context,
                REQUEST_MAIN_ACTIVITY,
                activityIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

            RemoteViews(
                context.packageName,
                R.layout.widget_weather
            ).apply {
                setOnClickPendingIntent(R.id.tv_message, servicePendingIntent)
                setOnClickPendingIntent(R.id.container, activityPendingIntent)
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
