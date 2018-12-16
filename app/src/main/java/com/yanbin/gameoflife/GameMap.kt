package com.yanbin.gameoflife


class GameMap(val width: Int, val height: Int) {

    var lifes: Array<Array<Boolean>> = Array(height + 2) { Array(width + 2) { false } }
    var nextGeneration: Array<Array<Boolean>> = Array(height + 2) { Array(width + 2) { false } }

    fun setSeeds(seeds: List<Position>) {
        seeds.forEach {
            lifes[it.y + 1][it.x + 1] = true
        }
    }

    fun nextRound() {
        lifes.forEachIndexed { y, rowLifes ->
            if (y != 0 && y != lifes.size - 1) {
                rowLifes.forEachIndexed { x, life ->
                    if (x != 0 && x != rowLifes.size - 1) {
                        val numOfNeighbors = getNeighbors(x, y)
                        if(life){
                            if (numOfNeighbors < 2) {
                                nextGeneration[y][x] = false
                            }else if(numOfNeighbors < 4){
                                nextGeneration[y][x] = life
                            }else{
                                nextGeneration[y][x] = false
                            }
                        }else{
                            if (numOfNeighbors == 3){
                                nextGeneration[y][x] = true
                            }
                        }

                    }
                }
            }
        }
        switchBuffer()
    }

    private fun switchBuffer() {
        val tempLife = nextGeneration
        nextGeneration = Array(height + 2) { Array(width + 2) { false } }
        lifes = tempLife
    }

    private fun getNeighbors(x: Int, y: Int): Int {
        var count = 0
        for (i in x - 1..x + 1) {
            for (j in y - 1..y + 1) {
                if (lifes[j][i] && !(i == x && j == y)) {
                    count++
                }
            }
        }
        return count
    }

    fun getCurrentLifes(): List<Position> {
        val currentLifes = mutableListOf<Position>()
        lifes.forEachIndexed { y, rowLifes ->
            if (y != 0 && y != lifes.size - 1) {
                rowLifes.forEachIndexed { x, life ->
                    if (x != 0 && x != rowLifes.size - 1) {
                        if (lifes[y][x]) {
                            currentLifes.add(Position(x - 1, y - 1))
                        }
                    }
                }
            }
        }

        return currentLifes
    }

}

data class Position(val x: Int, val y: Int)