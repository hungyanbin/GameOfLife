package com.yanbin.gameoflife

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameMapViewModel(private val gameMap: GameMap): ViewModel(){

//    val liveGameStatus = MutableLiveData<GameStatus>()
    val liveLifes = MutableLiveData<Lives>()
    val gameSpeed = MutableLiveData<Int>()

    fun onTouchAt(x: Int, y: Int){
        gameMap.setSeeds(CellFactory.smallShip(x, y))
    }

    fun onStartClicked(){
        gameMap.nextRound()
        liveLifes.postValue(gameMap.getCurrentLifes())
    }

    fun onStopClicked(){

    }
}

enum class GameStatus{
    PLAYING, IDLE, PAUSE
}