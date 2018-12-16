package com.yanbin.gameoflife

object CellFactory{

    val smallShip = listOf(
        Position(0, 1),
        Position(1, 2),
        Position(2, 0),
        Position(2, 1),
        Position(2, 2)
    )

    fun smallShip(xOffset: Int, yOffset: Int): List<Position> {
        return smallShip.map {
            Position(it.x + xOffset, it.y + yOffset)
        }
    }
}