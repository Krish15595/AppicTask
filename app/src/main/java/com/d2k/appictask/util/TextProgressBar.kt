package com.d2k.appictask.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.ProgressBar

class TextProgressBar : ProgressBar {
    private var text = ""
    private var textPaint: Paint
    private var size = 11
    private var padding = 29
    private var color = -0x1

    constructor(context: Context?) : super(context) {
        text = "HP"
        textPaint = Paint()
        textPaint.color = Color.BLACK
        textPaint.textSize = size.toFloat()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        text = "HP"
        textPaint = Paint()
        textPaint.color = Color.BLACK
        textPaint.textSize = size.toFloat()
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle) {
        text = "HP"
        textPaint = Paint()
        textPaint.color = Color.BLACK
        textPaint.textSize = size.toFloat()
    }

    fun setSize(size: Int, padding: Int) {
//        this.size = size;
        this.padding = padding
        drawableStateChanged()
    }

    fun setSize(size: Int, padding: Int, isDashboard: Boolean) {
        this.size = size
        this.padding = padding
        drawableStateChanged()
    }

    @Synchronized
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val density = context.resources.displayMetrics.density
        val textPaint = Paint()
        textPaint.isAntiAlias = true
        textPaint.color = color
        textPaint.isFakeBoldText = true
        textPaint.textSize = size * density
        val bounds = Rect()
        textPaint.getTextBounds(text, 0, text.length, bounds)
        val percentage = progress / 100.0f
        val x = width * percentage - padding * density
        val y = height / 2 - bounds.centerY().toFloat()
        canvas.drawText(text, x, y, textPaint)
    }

    @Synchronized
    fun setText(text: String) {
        this.text = text
        drawableStateChanged()
    }

    fun setTextColor(color: Int) {
        this.color = color
        drawableStateChanged()
    }
}