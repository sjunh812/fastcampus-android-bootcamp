package org.sjhstudio.fastcampus.part2.chapter2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class WaveFormView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val redPaint = Paint().apply { color = ContextCompat.getColor(context, R.color.red) }

    private var ampList = mutableListOf<Float>()
    private var rectList = mutableListOf<RectF>()

    private var tick = 0

    companion object {
        private const val RECT_WIDTH = 15f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        rectList.forEach { rectF ->
            canvas?.drawRect(rectF, redPaint)
        }
    }

    fun addAmplitude(maxAmplitude: Float) {
        // maxAmplitude 외 최댓값 : Short.MAX_VALUE
        val amplitude = (maxAmplitude / Short.MAX_VALUE) * height * 0.8f
        val maxRect = (width / RECT_WIDTH).toInt()

        rectList.clear()
        ampList.add(amplitude)
        ampList.takeLast(maxRect).forEachIndexed { i, amp ->
            val rectF = RectF().apply {
                top = (height / 2) - (amp / 2)
                bottom = top + amp
                left = i * RECT_WIDTH
                right = left + (RECT_WIDTH - 10f)
            }

            rectList.add(rectF)
        }

        invalidate()
    }

    fun replyAmplitude() {
        val maxRect = (width / RECT_WIDTH).toInt()

        rectList.clear()
        ampList.take(tick++).takeLast(maxRect).forEachIndexed { i, amp ->
            val rectF = RectF().apply {
                top = (height / 2) - (amp / 2)
                bottom = top + amp
                left = i * RECT_WIDTH
                right = left + (RECT_WIDTH - 10f)
            }

            rectList.add(rectF)
        }

        invalidate()
    }

    fun clearData() {
        ampList.clear()
    }

    fun clearWave() {
        rectList.clear()
        tick = 0

        invalidate()
    }
}