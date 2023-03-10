package org.sjhstudio.fastcampus.part2.chapter2

import android.os.Handler
import android.os.Looper

class Timer(tickListener: OnTimerTickListener) {

    private var duration: Long = 0L
    private val handler: Handler = Handler(Looper.getMainLooper())
    private val runnable: Runnable = object : Runnable {
        override fun run() {
            duration += TIMER_DELAY
            handler.postDelayed(this, TIMER_DELAY)
            tickListener.onTick(duration)
        }
    }

    companion object {
        const val TIMER_DELAY = 10L
    }

    fun start() = handler.postDelayed(runnable, TIMER_DELAY)

    fun pause() = handler.removeCallbacks(runnable)

    fun stop() {
        handler.removeCallbacks(runnable)
        duration = 0L
    }
}

interface OnTimerTickListener {

    fun onTick(duration: Long)
}