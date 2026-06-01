package com.iamconanpeter.chromadrop.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.iamconanpeter.chromadrop.engine.model.CellColor
import com.iamconanpeter.chromadrop.engine.model.GameSnapshot
import kotlin.math.min

@Composable
fun BoardCanvas(
    snapshot: GameSnapshot,
    modifier: Modifier = Modifier,
) {
    val activeCells = snapshot.activePair?.cells().orEmpty().associateBy { it.row to it.col }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(480.dp)
            .background(Color(0xFF101823), RoundedCornerShape(12.dp)),
    ) {
        val rows = snapshot.board.rows
        val cols = snapshot.board.cols
        val cell = min(size.width / cols, size.height / rows)
        val offsetX = (size.width - cols * cell) / 2f
        val offsetY = (size.height - rows * cell) / 2f

        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val topLeft = Offset(
                    x = offsetX + col * cell,
                    y = offsetY + row * cell,
                )

                drawRect(
                    color = Color(0xFF1D2B3A),
                    topLeft = topLeft,
                    size = Size(cell - 2f, cell - 2f),
                )

                val active = activeCells[row to col]?.color
                val settled = snapshot.board.cells[row][col]
                val color = active ?: settled
                if (color != null) {
                    drawRect(
                        color = color.toUiColor(),
                        topLeft = Offset(topLeft.x + 2f, topLeft.y + 2f),
                        size = Size(cell - 6f, cell - 6f),
                    )
                }
            }
        }
    }
}

private fun CellColor.toUiColor(): Color {
    return when (this) {
        CellColor.RED -> Color(0xFFE85D75)
        CellColor.GREEN -> Color(0xFF67C587)
        CellColor.BLUE -> Color(0xFF58A6FF)
        CellColor.YELLOW -> Color(0xFFF7C14D)
    }
}
