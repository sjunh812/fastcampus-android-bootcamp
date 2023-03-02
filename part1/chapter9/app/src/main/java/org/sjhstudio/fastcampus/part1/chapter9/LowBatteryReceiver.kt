package org.sjhstudio.fastcampus.part1.chapter9

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class LowBatteryReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.i("onReceive()", "${intent.action}")
        Toast.makeText(context, "배터리가 부족합니다.", Toast.LENGTH_SHORT).show()
    }
}