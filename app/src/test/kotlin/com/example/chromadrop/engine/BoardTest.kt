package com.example.chromadrop.engine

import org.junit.Assert.*
import org.junit.Test

class BoardTest {
    @Test
    fun testPlacePiece_success() {
        val board = Board.empty()
        val piece = Piece(arrayOf(intArrayOf(0))) // single block of color 0
        val newBoard = board.placePiece(piece, 0, 0)
        assertNotNull(newBoard)
        val grid = newBoard!!.getGridCopy()
        assertEquals(0, grid[0][0])
        assertEquals(Board.EMPTY, grid[0][1])
    }

    @Test
    fun testPlacePiece_collision() {
        val board = Board.empty()
        val piece1 = Piece(arrayOf(intArrayOf(1)))
        val boardAfter1 = board.placePiece(piece1, 0, 0)!!
        val piece2 = Piece(arrayOf(intArrayOf(2)))
        // Attempt to place overlapping
        val newBoard = boardAfter1.placePiece(piece2, 0, 0)
        assertNull(newBoard)
    }

    @Test
    fun testDetectClusters() {
        val board = Board.empty()
        // Place a 2x2 cluster of color 0
        val piece = Piece(arrayOf(intArrayOf(0,0), intArrayOf(0,0)))
        val b1 = board.placePiece(piece, 0, 0)!!.placePiece(piece, 4, 0)!!
        // Should detect one cluster of size 8
        val clusters = b1.detectClusters(minSize=4)
        assertEquals(1, clusters.size)
        assertEquals(8, clusters[0].size)
    }

    @Test
    fun testClearClusters() {
        val board = Board.empty()
        val piece = Piece(arrayOf(intArrayOf(1,1), intArrayOf(1,1)))
        val board1 = board.placePiece(piece, 0, 0)!!.placePiece(piece, 4, 0)!!
        val clusters = board1.detectClusters(minSize=4)
        val cleared = board1.clearClusters(clusters)
        val grid = cleared.getGridCopy()
        assertEquals(Board.EMPTY, grid[0][0])
        assertEquals(Board.EMPTY, grid[0][4])
    }

    @Test
    fun testApplyGravity() {
        val board = Board.empty()
        // Place a block at (0,0) and an empty at (0,1)
        val piece = Piece(arrayOf(intArrayOf(3)))
        val board1 = board.placePiece(piece, 0, 0)!!
        val board2 = board1.applyGravity()
        val grid = board2.getGridCopy()
        assertEquals(Board.EMPTY, grid[0][0])
        assertEquals(3, grid[Board.HEIGHT-1][0])
    }
}
