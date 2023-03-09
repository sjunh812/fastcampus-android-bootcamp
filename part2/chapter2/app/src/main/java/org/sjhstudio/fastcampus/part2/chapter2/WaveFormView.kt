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

    private val rectF = RectF()
    private val redPaint = Paint().apply { color = ContextCompat.getColor(context, R.color.red) }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRect(rectF, redPaint)
    }

    fun addAmplitude(maxAmplitude: Float) {
        rectF.apply {
            top = 0f
            bottom = maxAmplitude
            left = 0f
            right = 20f
        }

        invalidate()
    }
}