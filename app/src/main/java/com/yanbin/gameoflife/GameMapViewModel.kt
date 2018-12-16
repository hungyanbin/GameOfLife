package com.yanbin.gameoflife

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

class GameMapViewModel(private val gameMap: GameMap): ViewModel(){

    val liveLives = MutableLiveData<Lives>()
    var gameSpeed = AtomicInteger(MIN_UPDATE_PERIOD)
    val running = AtomicBoolean(true)

    var gameJob: Job? = null

    fun onStart(){
        gameStart()
    }

    private fun gameStart() {
        gameJob = GlobalScope.launch {
            while (running.get()) {
                val speed = gameSpeed.get()
                delay(GameView.MIN_UPDATE_PERIOD + (speed * speed) / 50f.toLong())
                gameMap.nextRound()
                liveLives.postValue(gameMap.getCurrentLifes())
            }
        }
    }

    fun onTouchAt(x: Int, y: Int){
        gameMap.setSeeds(CellFactory.smallShip(x, y))
        liveLives.postValue(gameMap.getCurrentLifes())
    }

    fun onStartClicked(){
        running.set(true)
        gameStart()
    }

    fun onStopClicked(){
        running.set(false)
    }

    override fun onCleared() {
        gameJob!!.cancel()
    }

    companion object {
        const val MIN_UPDATE_PERIOD = 10
    }
}