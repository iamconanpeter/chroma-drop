package com.conan.chromadrop

import org.junit.Test
import com.google.common.truth.Truth.assertThat

class BoardTest {
    @Test
    fun testClearLine() {
        val board = Board(width = 6, height = 12)
        // Fill a column with same color blocks
        for (row in 0 until 6) {
            board.setCell(row, 5, Block(Color.RED))
        }
        // Trigger clear detection
        board.checkAndClear()
        // Expect column cleared (all null)
        for (row in 0 until 6) {
            assertThat(board.getCell(row, 5)).isNull()
        }
    }
}
