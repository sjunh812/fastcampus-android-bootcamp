package org.sjhstudio.fastcampus.part2.chapter12

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout

class ExoPlayerMotionLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MotionLayout(context, attrs, defStyleAttr) {

    var targetView: View? = null
    private val gestureDetector by lazy {
        GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
                Log.e("sjh", "${targetView?.containTouchArea(e1.x.toInt(), e1.y.toInt())}")
                return targetView?.containTouchArea(e1.x.toInt(), e1.y.toInt()) ?: false
            }
        })
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        return event?.let {
            gestureDetector.onTouchEvent(it)
        } ?: false
    }
}