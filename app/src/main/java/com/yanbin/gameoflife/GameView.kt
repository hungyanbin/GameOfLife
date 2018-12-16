package com.yanbin.gameoflife

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

class GameView : View {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val isRunning: AtomicBoolean = AtomicBoolean(false)
    private val updateFreq: AtomicInteger = AtomicInteger(MIN_UPDATE_PERIOD)

    companion object {
        const val CELL_SIZE: Float = 5f
        const val MIN_UPDATE_PERIOD = 10
    }

    private val cellPaint = Paint().apply {
        color = Color.BLACK
    }

    private var gameMap: GameMap = GameMap((400/CELL_SIZE.toInt()).dpToPx(), (400/CELL_SIZE.toInt()).dpToPx()).apply {
        setSeeds(GameTemplates.smallShip)
    }

    fun clear(){
        gameMap.clear()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val y = event.y / CELL_SIZE
                val x = event.x / CELL_SIZE
                gameMap.setSeeds(GameTemplates.smallShip(x.toInt(), y.toInt()))
                return true
            }
            MotionEvent.ACTION_UP -> {
                return false
            }
        }

        return super.onTouchEvent(event)
    }

    fun start() {
        isRunning.set(true)
        invalidate()
    }

    fun stop() {
        isRunning.set(false)
    }

    fun setUpdateFreq(freq: Int) {
        updateFreq.set(MIN_UPDATE_PERIOD + (freq * freq) / 50f.toInt())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val cells = gameMap.getCurrentLifes()

        cells.forEach { (x, y) ->
            val left = x * CELL_SIZE
            val top = y * CELL_SIZE
            val right = (x + 1) * CELL_SIZE
            val bottom = (y + 1) * CELL_SIZE

            canvas.drawRect(left, top, right, bottom, cellPaint)
        }
        gameMap.nextRound()

        postDelayed({
            if (isRunning.get()) {
                invalidate()
            }
        }, updateFreq.get().toLong())
    }

}