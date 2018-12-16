package com.yanbin.gameoflife

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class GameView : View {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var lives: Lives = listOf()

    companion object {
        const val CELL_SIZE: Float = 5f
    }

    private val cellPaint = Paint().apply {
        color = Color.BLACK
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        lives.forEach { (x, y) ->
            val left = x * CELL_SIZE
            val top = y * CELL_SIZE
            val right = (x + 1) * CELL_SIZE
            val bottom = (y + 1) * CELL_SIZE

            canvas.drawRect(left, top, right, bottom, cellPaint)
        }
    }

}