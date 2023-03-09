package org.sjhstudio.fastcampus.part2.chapter2

import android.os.Handler
import android.os.Looper

class Timer(tickListener: OnTimerTickListener) {

    private var duration: Long = 0L
    private val handler: Handler = Handler(Looper.getMainLooper())
    private val runnable: Runnable = object : Runnable {
        override fun run() {
            tickListener.onTick(duration)
            duration += TIMER_DELAY
            handler.postDelayed(this, TIMER_DELAY)
        }
    }

    companion object {
        const val TIMER_DELAY = 100L
    }

    fun start() {
        handler.postDelayed(runnable, TIMER_DELAY)
    }

    fun stop() {
        handler.removeCallbacks(runnable)
        duration = 0L
    }
}

interface OnTimerTickListener {

    fun onTick(duration: Long)
}