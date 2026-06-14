package com.example.chromadrop

data class GameState(
    val board: Array<IntArray>, // 0 empty, other ints represent colors
    val score: Int = 0,
    val combo: Int = 0,
    val nextBlock: Block? = null,
    val seed: Long = System.currentTimeMillis()
) {
    companion object {
        const val WIDTH = 6
        const val HEIGHT = 12
        fun empty(seed: Long = System.currentTimeMillis()) = GameState(
            board = Array(HEIGHT) { IntArray(WIDTH) { 0 } },
            seed = seed
        )
    }
}

data class Block(val shape: Array<IntArray>, val color: Int)
