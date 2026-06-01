package com.iamconanpeter.chromadrop.engine.model

data class Board(
    val rows: Int,
    val cols: Int,
    val cells: List<List<CellColor?>>,
) {
    companion object {
        fun empty(rows: Int, cols: Int): Board {
            return Board(rows, cols, List(rows) { List(cols) { null } })
        }
    }
}
