package com.iamconanpeter.chromadrop.engine

import com.iamconanpeter.chromadrop.engine.model.CellColor
import com.iamconanpeter.chromadrop.engine.model.PairColors
import java.time.LocalDate

interface RandomProvider {
    fun nextInt(bound: Int): Int
    fun stateToken(): Long
    fun restore(token: Long)
}

class SeededRandomProvider(seed: Long) : RandomProvider {
    private var state: Long = seed.takeUnless { it == 0L } ?: 0x6A09E667F3BCC909L

    override fun nextInt(bound: Int): Int {
        require(bound > 0)
        state = state * 6364136223846793005L + 1442695040888963407L
        val candidate = (state ushr 32).toInt() and Int.MAX_VALUE
        return candidate % bound
    }

    override fun stateToken(): Long = state

    override fun restore(token: Long) {
        state = token
    }
}

object SeedFactory {
    fun endless(seed: Long): Long {
        return if (seed == 0L) 0xC0FFEE11L else seed
    }

    fun daily(date: LocalDate): Long {
        return date.toEpochDay() xor 0x5A5A5A5AL
    }
}

fun RandomProvider.nextPairColors(): PairColors {
    val values = CellColor.values()
    return PairColors(
        pivot = values[nextInt(values.size)],
        satellite = values[nextInt(values.size)],
    )
}
