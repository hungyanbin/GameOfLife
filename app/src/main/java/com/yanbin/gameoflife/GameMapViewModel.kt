package com.yanbin.gameoflife

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

class GameMapViewModel(private val gameMap: GameMap) : ViewModel() {

    val liveLives = MutableLiveData<Lives>()
    var gameSpeed = AtomicInteger(MIN_UPDATE_PERIOD)
    val running = AtomicBoolean(true)
    var shipTemplate: (Int, Int) -> Lives = CellFactory::smallShip

    private var gameJob: Job? = null
    private var gameContext = newSingleThreadContext("MyOwnThread")

    fun onStart() {
        gameStart()
    }

    private fun gameStart() {
        gameJob = GlobalScope.launch(gameContext) {
            while (running.get()) {
                val speed = gameSpeed.get()
                delay(MIN_UPDATE_PERIOD + (speed * speed) / 50f.toLong())
                gameMap.nextRound()
                liveLives.postValue(gameMap.getCurrentLifes())
            }
        }
    }

    fun onTouchAt(x: Int, y: Int) {
        GlobalScope.launch(gameContext) {
            gameMap.setSeeds(shipTemplate(x, y))
            liveLives.postValue(gameMap.getCurrentLifes())
        }
    }

    fun onStartClicked() {
        running.set(true)
        gameStart()
    }

    fun onStopClicked() {
        running.set(false)
    }

    override fun onCleared() {
        gameJob!!.cancel()
    }

    fun clear() {
        GlobalScope.launch(gameContext) {
            gameMap.clear()
            liveLives.postValue(gameMap.getCurrentLifes())
        }
    }

    companion object {
        const val MIN_UPDATE_PERIOD = 10
    }
}