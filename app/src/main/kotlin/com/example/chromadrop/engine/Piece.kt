package com.example.chromadrop.engine

/** Simple piece definition. The shape is a 2‑D array where each cell is either EMPTY or a color integer */
class Piece(val shape: Array<IntArray>) {
    companion object {
        const val EMPTY = Board.EMPTY
    }
}
