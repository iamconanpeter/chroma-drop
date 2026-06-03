package com.example.chromadrop.engine

/**
 * Immutable representation of the game board.
 * Empty cells are represented with -1.
 */
class Board private constructor(private val grid: Array<IntArray>) {
    companion object {
        const val WIDTH = 10
        const val HEIGHT = 16
        const val EMPTY = -1

        /** Create an empty board */
        fun empty(): Board {
            val arr = Array(HEIGHT) { IntArray(WIDTH) { EMPTY } }
            return Board(arr)
        }
    }

    /** Return a copy of the board (deep copy of grid) */
    private fun copyGrid(): Array<IntArray> {
        return Array(HEIGHT) { row -> grid[row].clone() }
    }

    /**
     * Attempt to place a piece on the board at the given top‑left coordinates.
     * Returns a new Board instance on success, or null if placement is invalid.
     */
    fun placePiece(piece: Piece, topLeftX: Int, topLeftY: Int): Board? {
        // Bounds check
        if (topLeftX < 0 || topLeftY < 0) return null
        val pieceHeight = piece.shape.size
        val pieceWidth = piece.shape[0].size
        if (topLeftX + pieceWidth > WIDTH || topLeftY + pieceHeight > HEIGHT) return null
        // Collision detection
        for (y in 0 until pieceHeight) {
            for (x in 0 until pieceWidth) {
                val cell = piece.shape[y][x]
                if (cell != Piece.EMPTY) {
                    if (grid[topLeftY + y][topLeftX + x] != EMPTY) return null
                }
            }
        }
        // Apply placement on a copy
        val newGrid = copyGrid()
        for (y in 0 until pieceHeight) {
            for (x in 0 until pieceWidth) {
                val cell = piece.shape[y][x]
                if (cell != Piece.EMPTY) {
                    newGrid[topLeftY + y][topLeftX + x] = cell
                }
            }
        }
        return Board(newGrid)
    }

    /** Detect clusters of 4 or more same‑color contiguous cells. */
    fun detectClusters(minSize: Int = 4): List<List<Pair<Int, Int>>> {
        val visited = Array(HEIGHT) { BooleanArray(WIDTH) }
        val clusters = mutableListOf<List<Pair<Int, Int>>>()
        for (y in 0 until HEIGHT) {
            for (x in 0 until WIDTH) {
                if (grid[y][x] != EMPTY && !visited[y][x]) {
                    val color = grid[y][x]
                    val queue = ArrayDeque<Pair<Int, Int>>()
                    val cluster = mutableListOf<Pair<Int, Int>>()
                    queue.add(Pair(x, y))
                    visited[y][x] = true
                    while (queue.isNotEmpty()) {
                        val (cx, cy) = queue.removeFirst()
                        cluster.add(Pair(cx, cy))
                        val dirs = listOf(Pair(0, -1), Pair(0, 1), Pair(-1, 0), Pair(1, 0))
                        for ((dx, dy) in dirs) {
                            val nx = cx + dx
                            val ny = cy + dy
                            if (nx in 0 until WIDTH && ny in 0 until HEIGHT) {
                                if (!visited[ny][nx] && grid[ny][nx] == color) {
                                    visited[ny][nx] = true
                                    queue.add(Pair(nx, ny))
                                }
                            }
                        }
                    }
                    if (cluster.size >= minSize) {
                        clusters.add(cluster)
                    }
                }
            }
        }
        return clusters
    }

    /** Clear given clusters and return a new board */
    fun clearClusters(clusters: List<List<Pair<Int, Int>>>): Board {
        val newGrid = copyGrid()
        for (cluster in clusters) {
            for ((x, y) in cluster) {
                newGrid[y][x] = EMPTY
            }
        }
        return Board(newGrid)
    }

    /** Apply gravity so blocks fall down into empty spaces */
    fun applyGravity(): Board {
        val newGrid = Array(HEIGHT) { IntArray(WIDTH) { EMPTY } }
        for (x in 0 until WIDTH) {
            var writeRow = HEIGHT - 1
            for (y in HEIGHT - 1 downTo 0) {
                val value = grid[y][x]
                if (value != EMPTY) {
                    newGrid[writeRow][x] = value
                    writeRow--
                }
            }
        }
        return Board(newGrid)
    }

    /** Expose the grid for testing (read‑only) */
    fun getGridCopy(): Array<IntArray> = copyGrid()
