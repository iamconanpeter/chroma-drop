package com.iamconanpeter.chromadrop.engine.model

enum class Orientation(val rowOffset: Int, val colOffset: Int) {
    UP(-1, 0),
    RIGHT(0, 1),
    DOWN(1, 0),
    LEFT(0, -1);

    fun rotateClockwise(): Orientation {
        return when (this) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
        }
    }
}
