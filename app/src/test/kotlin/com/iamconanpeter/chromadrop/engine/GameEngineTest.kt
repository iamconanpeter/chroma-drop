package com.iamconanpeter.chromadrop.engine

import com.iamconanpeter.chromadrop.engine.model.Board
import com.iamconanpeter.chromadrop.engine.model.CellColor
import com.iamconanpeter.chromadrop.engine.model.FallingPair
import com.iamconanpeter.chromadrop.engine.model.Orientation
import com.iamconanpeter.chromadrop.engine.model.PairColors
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

class GameEngineTest {
    @Test
    fun engineSpawnsInitialPair() {
        val engine = GameEngine(rows = 8, cols = 6, seed = 7L, mode = GameMode.ENDLESS)

        val snapshot = engine.snapshot()
        assertFalse(snapshot.isGameOver)
        assertTrue(snapshot.activePair != null)
    }

    @Test
    fun dailySeedDeterminismProducesSameRun() {
        val seed = SeedFactory.daily(LocalDate.of(2026, 5, 6))
        val first = GameEngine(rows = 8, cols = 6, seed = seed, mode = GameMode.DAILY)
        val second = GameEngine(rows = 8, cols = 6, seed = seed, mode = GameMode.DAILY)

        repeat(12) {
            first.hardDrop()
            second.hardDrop()
            assertEquals(first.snapshot().score, second.snapshot().score)
            assertEquals(first.snapshot().activePair?.colors, second.snapshot().activePair?.colors)
            assertEquals(first.snapshot().nextPair, second.snapshot().nextPair)
            assertEquals(first.snapshot().board.cells, second.snapshot().board.cells)
        }
    }

    @Test
    fun undoRestoresBoardScoreAndRngPath() {
        val engine = GameEngine(rows = 8, cols = 6, seed = 42L, mode = GameMode.ENDLESS)

        val beforeLock = engine.snapshot()
        engine.hardDrop()
        val afterLock = engine.snapshot()

        assertTrue(afterLock.undoAvailable)

        engine.undo()
        val restored = engine.snapshot()
        assertEquals(beforeLock.board.cells, restored.board.cells)
        assertEquals(beforeLock.score, restored.score)
        assertEquals(beforeLock.nextPair, restored.nextPair)

        engine.hardDrop()
        val replayed = engine.snapshot()
        assertEquals(afterLock.board.cells, replayed.board.cells)
        assertEquals(afterLock.score, replayed.score)
        assertEquals(afterLock.nextPair, replayed.nextPair)

        engine.undo()
        assertEquals(replayed.board.cells, engine.snapshot().board.cells)
    }

    @Test
    fun cascadeResolvesAcrossMultipleWaves() {
        val engine = GameEngine(rows = 6, cols = 4, seed = 11L, mode = GameMode.ENDLESS)
        engine.debugSetBoard(
            Board(
                rows = 6,
                cols = 4,
                cells = listOf(
                    listOf(null, null, null, null),
                    listOf(CellColor.BLUE, null, null, null),
                    listOf(CellColor.BLUE, null, null, null),
                    listOf(CellColor.RED, CellColor.BLUE, null, null),
                    listOf(CellColor.RED, CellColor.BLUE, null, null),
                    listOf(CellColor.RED, CellColor.RED, null, null),
                ),
            )
        )
        engine.debugForceActivePair(
            FallingPair(
                pivotRow = 0,
                pivotCol = 3,
                orientation = Orientation.UP,
                colors = PairColors(CellColor.YELLOW, CellColor.GREEN),
            )
        )
        engine.debugSetNextPair(PairColors(CellColor.YELLOW, CellColor.GREEN))

        engine.hardDrop()
        val snapshot = engine.snapshot()

        assertEquals(2, snapshot.cascadeWavesLastStep)
        assertEquals(8, snapshot.clearedLastStep)
        assertTrue(snapshot.score >= 80)
    }
}
