package com.example.chromadrop

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class GameView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val paint = Paint().apply {
        this.isAntiAlias = true
        this.color = Color.WHITE
    }

    private val blockSize = 64f
    private val gridWidth = 8
    private val gridHeight = 12
    private val blocks = mutableListOf<Block>()
    private var score = 0
    private val scorePaint = Paint().apply {
        color = Color.BLACK
        textSize = 48f
    }

    init {
        // Initialize empty grid
        // Blocks will be added during game initialization
    }

    fun startGame() {
        // Reset game state
        blocks.clear()
        score = 0
        // TODO: Generate daily challenge piece sequence
        // For MVP, start with one block
        blocks.add(Block(0, 0, Color.RED)) // Starting block at top-left
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw grid
        for (x in 0 until gridWidth) {
            for (y in 0 until gridHeight) {
                val xPos = x * blockSize.toInt()
                val yPos = y * blockSize.toInt()
                paint.color = Color.GRAY
                canvas.drawRect(xPos.toDouble(), yPos.toDouble(), (xPos + blockSize).toDouble(), (yPos + blockSize).toDouble())
            }
        }

        // Draw blocks
        for (block in blocks) {
            paint.color = block.color
            canvas.drawRect(
                block.x * blockSize.toDouble(),
                block.y * blockSize.toDouble(),
                (block.x + 1) * blockSize.toDouble(),
                (block.y + 1) * blockSize.toDouble()
            )
        }

        // Draw score
        canvas.drawText("Score: $score", 10f, 60f, scorePaint)
    }

    fun tap(x: Float, y: Float) {
        // Convert touch coordinates to grid position
        val gridX = (x / blockSize).toInt()
        val gridY = (y / blockSize).toInt()

        // Check if we're tapping on a block
        val targetBlock = blocks.find { it.x == gridX && it.y == gridY }
        if (targetBlock != null) {
            // Rotate the block (cycle through orientations)
            // In a real implementation, this would handle 4 orientations
            // For MVP, we'll just add a new block for demonstration
            blocks.add(Block(gridX, gridY, targetBlock.color))
        }
    }

    data class Block(val x: Int, val y: Int, val color: Int)
}