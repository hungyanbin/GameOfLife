package com.yanbin.gameoflife

object CellFactory {

    private val smallShip = listOf(
        Position(0, 1),
        Position(1, 2),
        Position(2, 0),
        Position(2, 1),
        Position(2, 2)
    )

    private val mediumShip = listOf(
        Position(0, 1),
        Position(0, 3),
        Position(1, 0),
        Position(2, 0),
        Position(3, 0),
        Position(3, 3),
        Position(4, 0),
        Position(4, 1),
        Position(4, 2)
    )

    private val gun = listOf(
        Position(0, 4), Position(0, 5),
        Position(1, 4), Position(1, 5),
        Position(10, 4), Position(10, 5), Position(10, 6),
        Position(11, 3), Position(11, 7),
        Position(12, 2), Position(12, 8),
        Position(13, 2), Position(13, 8),
        Position(14, 5),
        Position(15, 3), Position(15, 7),
        Position(16, 4), Position(16, 5), Position(16, 6),
        Position(17, 5),
        Position(20, 2), Position(20, 3), Position(20, 4),
        Position(21, 2), Position(21, 3), Position(21, 4),
        Position(22, 1), Position(22, 5),
        Position(24, 0), Position(24, 1), Position(24, 5), Position(24, 6),
        Position(34, 2), Position(34, 3),
        Position(35, 2), Position(35, 3)
    )

    fun smallShip(xOffset: Int, yOffset: Int): List<Position> {
        return smallShip.map {
            Position(it.x + xOffset, it.y + yOffset)
        }
    }

    fun mediumShip(xOffset: Int, yOffset: Int): List<Position> {
        return mediumShip.map {
            Position(it.x + xOffset, it.y + yOffset)
        }
    }

    fun gun(xOffset: Int, yOffset: Int): List<Position>{
        return gun.map {
            Position(it.x + xOffset, it.y + yOffset)
        }
    }
}