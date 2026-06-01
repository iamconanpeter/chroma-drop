package com.iamconanpeter.chromadrop.engine

import com.iamconanpeter.chromadrop.engine.model.CellColor

data class CascadeResult(
    val clearedCells: Int,
    val waves: Int,
)

object GameRules {
    const val CLEAR_THRESHOLD: Int = 4

    fun resolveCascades(grid: Array<Array<CellColor?>>): CascadeResult {
        var totalCleared = 0
        var waves = 0

        while (true) {
            val clearSet = findClearSet(grid)
            if (clearSet.isEmpty()) break

            waves += 1
            totalCleared += clearSet.size

            clearSet.forEach { (row, col) ->
                grid[row][col] = null
            }
            applyGravity(grid)
        }

        return CascadeResult(clearedCells = totalCleared, waves = waves)
    }

    fun canPlace(grid: Array<Array<CellColor?>>, row: Int, col: Int): Boolean {
        if (col !in grid[0].indices) return false
        if (row >= grid.size) return false
        return row < 0 || grid[row][col] == null
    }

    private fun findClearSet(grid: Array<Array<CellColor?>>): Set<Pair<Int, Int>> {
        val visited = Array(grid.size) { BooleanArray(grid[0].size) }
        val toClear = mutableSetOf<Pair<Int, Int>>()

        for (row in grid.indices) {
            for (col in grid[0].indices) {
                val color = grid[row][col] ?: continue
                if (visited[row][col]) continue

                val cluster = mutableListOf<Pair<Int, Int>>()
                val queue = ArrayDeque<Pair<Int, Int>>()
                queue.add(row to col)
                visited[row][col] = true

                while (queue.isNotEmpty()) {
                    val (currentRow, currentCol) = queue.removeFirst()
                    cluster.add(currentRow to currentCol)

                    val neighbors = listOf(
                        currentRow - 1 to currentCol,
                        currentRow + 1 to currentCol,
                        currentRow to currentCol - 1,
                        currentRow to currentCol + 1,
                    )

                    for ((nextRow, nextCol) in neighbors) {
                        if (nextRow !in grid.indices || nextCol !in grid[0].indices) continue
                        if (visited[nextRow][nextCol]) continue
                        if (grid[nextRow][nextCol] != color) continue

                        visited[nextRow][nextCol] = true
                        queue.add(nextRow to nextCol)
                    }
                }

                if (cluster.size >= CLEAR_THRESHOLD) {
                    toClear.addAll(cluster)
                }
            }
        }

        return toClear
    }

    private fun applyGravity(grid: Array<Array<CellColor?>>) {
        val rows = grid.size
        val cols = grid[0].size

        for (col in 0 until cols) {
            var writeRow = rows - 1
            for (row in rows - 1 downTo 0) {
                val color = grid[row][col] ?: continue
                grid[writeRow][col] = color
                if (writeRow != row) {
                    grid[row][col] = null
                }
                writeRow -= 1
            }
            while (writeRow >= 0) {
                grid[writeRow][col] = null
                writeRow -= 1
            }
        }
    }
}
