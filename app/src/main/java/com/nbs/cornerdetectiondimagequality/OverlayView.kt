package com.nbs.cornerdetectiondimagequality

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class OverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        color = ContextCompat.getColor(context, android.R.color.black) // Dark color
        alpha = 150 // Adjust transparency (0-255)
        style = Paint.Style.FILL
    }
    private val clearPaint = Paint().apply {
        color = ContextCompat.getColor(context, android.R.color.transparent)
        xfermode = android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.CLEAR)
    }

    private val rect = RectF()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

        val margin = 50
        val rectWidth = width * 0.55f
        val rectHeight = height * 0.75f
        val left = (width - rectWidth) / 2
        val top = (height - rectHeight) / 2
        val right = left + rectWidth
        val bottom = top + rectHeight

        rect.set(left, top, right, bottom)

        // Clear the rectangle inside
        canvas.drawRect(rect, clearPaint)
    }
}
