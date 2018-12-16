package com.yanbin.gameoflife

import android.os.Bundle
import android.view.MotionEvent
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.yanbin.gameoflife.GameView.Companion.CELL_SIZE
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {

    private var gameMap: GameMap = GameMap((400 / GameView.CELL_SIZE.toInt()).dpToPx(),
        (400 / GameView.CELL_SIZE.toInt()).dpToPx())

    private val viewModel: GameMapViewModel by viewModel { parametersOf(gameMap) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        viewModel.liveLifes.observe(this, Observer {
            gameView.lives = it
            gameView.invalidate()
        })

        gameView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val y = event.y / CELL_SIZE
                    val x = event.x / CELL_SIZE
                    viewModel.onTouchAt(x.toInt(), y.toInt())
                    true
                }
                MotionEvent.ACTION_UP -> {
                    false
                }
                else -> {
                    false
                }
            }
        }

        btnStart.setOnClickListener {
            viewModel.onStartClicked()
        }

        btnStop.setOnClickListener {
            viewModel.onStopClicked()
        }

        btnClear.setOnClickListener {
            gameView.clear()
        }

        seekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                    gameView.setUpdateFreq(progress)
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                }
            }
        )
    }

}
