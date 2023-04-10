package org.sjhstudio.fastcampus.part2.chapter7.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class WeatherWidgetService : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }
}