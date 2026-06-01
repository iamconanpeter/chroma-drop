package com.iamconanpeter.chromadrop.engine.model

data class Cell(val row: Int, val col: Int, val color: CellColor)

data class PairColors(val pivot: CellColor, val satellite: CellColor)

data class FallingPair(
    val pivotRow: Int,
    val pivotCol: Int,
    val orientation: Orientation,
    val colors: PairColors,
) {
    fun cells(): List<Cell> {
        val satelliteRow = pivotRow + orientation.rowOffset
        val satelliteCol = pivotCol + orientation.colOffset
        return listOf(
            Cell(pivotRow, pivotCol, colors.pivot),
            Cell(satelliteRow, satelliteCol, colors.satellite),
        )
    }
}
