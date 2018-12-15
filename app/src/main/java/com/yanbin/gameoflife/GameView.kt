package com.yanbin.gameoflife

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.concurrent.atomic.AtomicBoolean

class GameView : View {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val isRunning: AtomicBoolean = AtomicBoolean(false)

    private val cellPaint = Paint().apply {
        color = Color.BLACK
    }

    private var gameMap: GameMap = GameMap(100, 100).apply {
        setSeeds(listOf(
            Position(0, 1),
            Position(1, 2),
            Position(2, 0),
            Position(2, 1),
            Position(2, 2)
        ))
    }

    private val CELL_SIZE: Float = 20f

    fun reset(positions: List<Position>){
        gameMap.setSeeds(positions)
    }

    fun start(){
        isRunning.set(true)
        invalidate()
    }

    fun stop(){
        isRunning.set(false)
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
            if(isRunning.get()){
                invalidate()
            }
        }, 100)
    }

}