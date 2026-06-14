package com.example.chromadrop

class GameEngine(initialState: GameState) {
    private var state = initialState

    fun dropBlock(block: Block): GameState {
        // Simplified: place block at top middle and let it fall until collision.
        // For brevity, we skip physics and just insert into board.
        // Real implementation would iterate rows.
        // Here we return a new state with block added.
        val newBoard = state.board.map { it.clone() }.toTypedArray()
        // find first empty column in middle
        val col = GameState.WIDTH / 2
        for (row in GameState.HEIGHT - 1 downTo 0) {
            if (newBoard[row][col] == 0) {
                newBoard[row][col] = block.color
                break
            }
        }
        val cleared = clearMatches(newBoard)
        val newScore = state.score + cleared * 10 * (state.combo + 1)
        val newCombo = if (cleared > 0) state.combo + 1 else 0
        return state.copy(board = newBoard, score = newScore, combo = newCombo)
    }

    private fun clearMatches(board: Array<IntArray>): Int {
        var cleared = 0
        for (r in board.indices) {
            var count = 0
            var lastColor = 0
            for (c in board[r].indices) {
                val col = board[r][c]
                if (col != 0 && col == lastColor) {
                    count++
                } else {
                    if (count >= 4) {
                        // clear previous run
                        for (i in c - count until c) {
                            board[r][i] = 0
                            cleared++
                        }
                    }
                    count = if (col != 0) 1 else 0
                }
                lastColor = col
            }
            if (count >= 4) {
                for (i in board[r].size - count until board[r].size) {
                    board[r][i] = 0
                    cleared++
                }
            }
        }
        return cleared
    }
}
