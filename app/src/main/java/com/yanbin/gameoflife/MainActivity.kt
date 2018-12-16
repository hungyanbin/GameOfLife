package com.yanbin.gameoflife

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

        viewModel.liveLives.observe(this, Observer {
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
            viewModel.clear()
        }

        seekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                    viewModel.gameSpeed.set(progress)
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                }
            }
        )
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_small -> viewModel.shipTemplate = CellFactory::smallShip
            R.id.menu_medium -> viewModel.shipTemplate = CellFactory::mediumShip
            R.id.menu_gun -> viewModel.shipTemplate = CellFactory::gun
        }
        return super.onOptionsItemSelected(item)
    }



}
