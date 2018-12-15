package com.yanbin.gameoflife


class GameMap(val width: Int, val height: Int) {

    var lifes: Array<Array<Boolean>> = Array(height + 2) { Array(width + 2) { false } }
    var nextGeneration: Array<Array<Boolean>> = Array(height + 2) { Array(width + 2) { false } }

    fun setSeeds(seeds: List<Position>) {
        seeds.forEach {
            lifes[it.x + 1][it.y + 1] = true
        }
    }

    fun nextRound() {
        lifes.forEachIndexed { x, rowLifes ->
            if (x != 0 && x != lifes.size - 1) {
                rowLifes.forEachIndexed { y, life ->
                    if (y != 0 && y != rowLifes.size - 1) {
                        val numOfNeighbors = getNeighbors(x, y)
                        if(life){
                            if (numOfNeighbors < 2) {
                                nextGeneration[x][y] = false
                            }else if(numOfNeighbors < 4){
                                nextGeneration[x][y] = life
                            }else{
                                nextGeneration[x][y] = false
                            }
                        }else{
                            if (numOfNeighbors == 3){
                                nextGeneration[x][y] = true
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
                if (lifes[i][j] && !(i == x && j == y)) {
                    count++
                }
            }
        }
        return count
    }

    fun getCurrentLifes(): List<Position> {
        val currentLifes = mutableListOf<Position>()
        lifes.forEachIndexed { x, rowLifes ->
            if (x != 0 && x != lifes.size - 1) {
                rowLifes.forEachIndexed { y, life ->
                    if (y != 0 && y != rowLifes.size - 1) {
                        if (lifes[x][y]) {
                            currentLifes.add(Position(y - 1, x - 1))
                        }
                    }
                }
            }
        }

        return currentLifes
    }

}

data class Position(val x: Int, val y: Int)