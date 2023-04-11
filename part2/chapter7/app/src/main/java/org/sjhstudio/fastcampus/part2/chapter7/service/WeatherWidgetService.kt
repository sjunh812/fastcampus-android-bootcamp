package org.sjhstudio.fastcampus.part2.chapter7.service

import android.Manifest
import android.app.*
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationServices
import org.sjhstudio.fastcampus.part2.chapter7.R
import org.sjhstudio.fastcampus.part2.chapter7.repository.WeatherRepository
import org.sjhstudio.fastcampus.part2.chapter7.util.updateRemoteViews

class WeatherWidgetService : Service() {

    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(this) }

    companion object {
        private const val LOG = "WeatherWidgetService"
        private const val WEATHER_WIDGET_FOREGROUND_SERVICE_ID = 100
        private const val REQUEST_APPLICATION_SETTINGS = 2
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(WEATHER_WIDGET_FOREGROUND_SERVICE_ID, createNotification())
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(STOP_FOREGROUND_REMOVE)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // todo. 위젯을 권한없음 상태로 표시하고, 클릭했을 때 권한팝업을 얻을 수 있도록 조정
            Log.e(LOG, "위젯 권한 없음")

            updateRemoteViews { remoteViews ->
                remoteViews.setTextViewText(R.id.tv_message, "권한 없음")
                remoteViews.setViewVisibility(R.id.tv_message, View.VISIBLE)
            }

            val settingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .apply { data = Uri.fromParts("package", packageName, null) }

            PendingIntent.getActivity(
                this,
                REQUEST_APPLICATION_SETTINGS,
                settingsIntent,
                PendingIntent.FLAG_IMMUTABLE
            ).send()

            stopSelf()

            return super.onStartCommand(intent, flags, startId)
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            WeatherRepository.getAddress(
                context = this,
                latitude = location.latitude,
                longitude = location.longitude,
                callback = { address ->
                    Log.e(LOG, address?.thoroughfare.orEmpty())

                    updateRemoteViews { remoteViews ->
                        remoteViews.setTextViewText(
                            R.id.tv_location,
                            address?.thoroughfare.orEmpty()
                        )
                        remoteViews.setViewVisibility(R.id.tv_message, View.GONE)
                    }
                }
            )

            WeatherRepository.getVillageForecast(
                latitude = location.latitude,
                longitude = location.longitude,
                successCallback = { forecastList ->
                    Log.e(LOG, "$forecastList")

                    updateRemoteViews { remoteViews ->
                        remoteViews.setTextViewText(
                            R.id.tv_tmp,
                            getString(R.string.format_temperature, forecastList.first().tmp)
                        )
                        remoteViews.setTextViewText(
                            R.id.tv_sky_pty,
                            forecastList.first().skyPty
                        )
                        remoteViews.setViewVisibility(R.id.tv_message, View.GONE)
                    }
                },
                failureCallback = { t ->
                    t.printStackTrace()

                    updateRemoteViews { remoteViews ->
                        remoteViews.setTextViewText(R.id.tv_message, "에러")
                        remoteViews.setViewVisibility(R.id.tv_message, View.VISIBLE)
                    }

                    stopSelf()
                }
            )
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            getString(R.string.id_weather_widget_channel),
            getString(R.string.app_name),
            NotificationManager.IMPORTANCE_LOW
        ).apply { description = getString(R.string.description_weather_widget_channel) }

        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
            .run { createNotificationChannel(channel) }
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, getString(R.string.id_weather_widget_channel))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.content_weather_widget_notification))
            .build()
    }
}