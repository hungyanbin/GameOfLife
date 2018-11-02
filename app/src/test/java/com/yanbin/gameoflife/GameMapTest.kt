package com.yanbin.gameoflife

import org.junit.Assert
import org.junit.Test

class GameMapTest{

    @Test
    fun `start With one neighbors should die`() {
        val gameMap = GameMap(3, 3)
        gameMap.setSeeds(listOf(Position(0, 0), Position(1, 1)))

        gameMap.nextRound()

        val nextRoundLifess = gameMap.getCurrentLifes()
        val expectLifes = listOf<Position>()
        Assert.assertEquals(expectLifes, nextRoundLifess)
    }

    @Test
    fun `start With two neighbors should stay life`() {
        val gameMap = GameMap(3, 3)
        gameMap.setSeeds(listOf(Position(0, 0), Position(0, 2), Position(1, 1)))

        gameMap.nextRound()

        val nextRoundLifess = gameMap.getCurrentLifes()
        val expectLifes = listOf<Position>(Position(0, 1), Position(1, 1))
        Assert.assertEquals(expectLifes, nextRoundLifess)
    }

    @Test
    fun `start With three neighbors`() {
        val gameMap = GameMap(3, 3)
        gameMap.setSeeds(listOf(Position(0, 0), Position(0, 1), Position(0, 2), Position(1, 1)))

        gameMap.nextRound()

        val nextRoundLifess = gameMap.getCurrentLifes()
        val expectLifes = listOf<Position>(Position(0, 0),
                Position(0,1),
                Position(0, 2),
                Position(1, 0),
                Position(1, 1),
                Position(1, 2))
        Assert.assertEquals(expectLifes, nextRoundLifess)
    }

    @Test
    fun `start With two neighbors should stay life2`() {
        val gameMap = GameMap(3, 3)
        gameMap.setSeeds(listOf(Position(0, 0), Position(0, 1), Position(0, 2), Position(1, 1)))

        gameMap.nextRound()
        gameMap.nextRound()

        val nextRoundLifess = gameMap.getCurrentLifes()
        val expectLifes = listOf<Position>(Position(0, 0),
                Position(0, 2),
                Position(1, 0),
                Position(1, 2),
                Position(2,1))
        Assert.assertEquals(expectLifes, nextRoundLifess)
    }
}
