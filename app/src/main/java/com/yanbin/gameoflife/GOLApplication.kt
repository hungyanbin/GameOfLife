package com.yanbin.gameoflife

import android.app.Application
import org.koin.android.ext.android.startKoin
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

class GOLApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(gameModule))
    }

}

val gameModule = module {
    viewModel { (gameMap: GameMap) ->
        GameMapViewModel(gameMap)
    }
}
