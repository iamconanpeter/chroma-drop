package com.example.chromadrop

import org.junit.Assert.assertEquals
import org.junit.Test

class GameEngineTest {
    @Test
    fun testClearFourInRow() {
        val emptyState = GameState.empty()
        val engine = GameEngine(emptyState)
        // Fill a row with four same colors manually
        val board = Array(GameState.HEIGHT) { IntArray(GameState.WIDTH) }
        board[GameState.HEIGHT - 1][0] = 1
        board[GameState.HEIGHT - 1][1] = 1
        board[GameState.HEIGHT - 1][2] = 1
        board[GameState.HEIGHT - 1][3] = 1
        val state = emptyState.copy(board = board)
        val engine2 = GameEngine(state)
        val result = engine2.dropBlock(Block(arrayOf(intArrayOf(0)), 2)) // dropping unrelated block
        // Expect the four matching blocks cleared (4) and score increased by 40 (10 * combo=1)
        assertEquals(0, result.board[GameState.HEIGHT - 1][0])
        assertEquals(0, result.board[GameState.HEIGHT - 1][1])
        assertEquals(0, result.board[GameState.HEIGHT - 1][2])
        assertEquals(0, result.board[GameState.HEIGHT - 1][3])
        assertEquals(40, result.score)
    }
}
