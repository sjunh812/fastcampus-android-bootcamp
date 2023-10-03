package com.example.chapter1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.util.AttributeSet
import android.util.SizeF
import android.view.View

class FaceOverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        alpha = 90
        style = Paint.Style.FILL
    }

    private val facePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GRAY
        style = Paint.Style.STROKE
        strokeWidth = 10F
        pathEffect = DashPathEffect(floatArrayOf(10F, 10F), 0F)
    }

    private val maskPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
    }

    private val facePath = Path()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawOverlay(canvas)
    }

    fun setSize(rectF: RectF, sizeF: SizeF, pointF: PointF) {
        val topOffset = sizeF.width / 2
        val bottomOffset = sizeF.width / 5

        with(facePath) {
            reset()
            moveTo(pointF.x, rectF.top) // 얼굴 윤곽선을 그릴 시작위치.
            cubicTo(    // 오른쪽 반원 그리기.
                rectF.right + topOffset,
                rectF.top,
                rectF.right + bottomOffset,
                rectF.bottom,
                pointF.x,
                rectF.bottom
            )
            cubicTo(    // 왼쪽 반원 그리기.
                rectF.left + bottomOffset,
                rectF.bottom,
                rectF.left + topOffset,
                rectF.top,
                pointF.x,
                rectF.top
            )
            close()
        }
        postInvalidate()
    }

    fun reset() {
        facePath.reset()
        invalidate()
    }

    private fun drawOverlay(canvas: Canvas) {
        canvas.drawRect(
            0F,
            0F,
            canvas.width.toFloat(),
            canvas.height.toFloat(),
            backgroundPaint
        )
        canvas.drawPath(facePath, facePaint)
        canvas.drawPath(facePath, maskPaint)
    }
}