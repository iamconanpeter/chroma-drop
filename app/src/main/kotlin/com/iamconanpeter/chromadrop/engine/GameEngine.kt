package com.iamconanpeter.chromadrop.engine

import com.iamconanpeter.chromadrop.engine.model.Board
import com.iamconanpeter.chromadrop.engine.model.CellColor
import com.iamconanpeter.chromadrop.engine.model.FallingPair
import com.iamconanpeter.chromadrop.engine.model.GameSnapshot
import com.iamconanpeter.chromadrop.engine.model.Orientation
import com.iamconanpeter.chromadrop.engine.model.PairColors

enum class GameMode {
    ENDLESS,
    DAILY,
}

class GameEngine(
    private val rows: Int = 16,
    private val cols: Int = 8,
    seed: Long,
    private val mode: GameMode,
    private val randomProvider: RandomProvider = SeededRandomProvider(seed),
) {
    private var board: Array<Array<CellColor?>> = emptyGrid(rows, cols)
    private var activePair: FallingPair? = null
    private var nextPair: PairColors = randomProvider.nextPairColors()
    private var score: Int = 0
    private var combo: Int = 0
    private var isPaused: Boolean = false
    private var isGameOver: Boolean = false
    private var clearedLastStep: Int = 0
    private var cascadesLastStep: Int = 0
    private var undoUsed: Boolean = false
    private var undoSnapshot: EngineState? = null

    init {
        spawnPair()
    }

    fun snapshot(): GameSnapshot {
        return GameSnapshot(
            board = toBoard(board),
            activePair = activePair,
            nextPair = nextPair,
            score = score,
            combo = combo,
            clearedLastStep = clearedLastStep,
            cascadeWavesLastStep = cascadesLastStep,
            dropIntervalMs = currentDropInterval(),
            isGameOver = isGameOver,
            isPaused = isPaused,
            undoAvailable = !undoUsed && undoSnapshot != null,
        )
    }

    fun tick(): GameSnapshot {
        if (isPaused || isGameOver) return snapshot()

        val pair = activePair
        if (pair == null) {
            spawnPair()
            return snapshot()
        }

        val moved = pair.copy(pivotRow = pair.pivotRow + 1)
        if (canPlace(moved)) {
            activePair = moved
            clearedLastStep = 0
            cascadesLastStep = 0
        } else {
            saveUndoStateIfEligible()
            lockPair(pair)
        }

        return snapshot()
    }

    fun softDrop(): GameSnapshot = tick()

    fun hardDrop(): GameSnapshot {
        if (isPaused || isGameOver) return snapshot()
        var pair = activePair ?: return snapshot()

        while (true) {
            val next = pair.copy(pivotRow = pair.pivotRow + 1)
            if (!canPlace(next)) break
            pair = next
        }

        activePair = pair
        saveUndoStateIfEligible()
        lockPair(pair)
        return snapshot()
    }

    fun moveLeft(): GameSnapshot = moveHorizontal(-1)

    fun moveRight(): GameSnapshot = moveHorizontal(1)

    fun rotateClockwise(): GameSnapshot {
        if (isPaused || isGameOver) return snapshot()
        val pair = activePair ?: return snapshot()

        val rotated = pair.copy(orientation = pair.orientation.rotateClockwise())
        val kicks = listOf(0, -1, 1, -2, 2)
        for (kick in kicks) {
            val candidate = rotated.copy(pivotCol = rotated.pivotCol + kick)
            if (canPlace(candidate)) {
                activePair = candidate
                break
            }
        }

        return snapshot()
    }

    fun pause() {
        isPaused = true
    }

    fun resume() {
        if (!isGameOver) {
            isPaused = false
        }
    }

    fun restart(seed: Long) {
        board = emptyGrid(rows, cols)
        activePair = null
        nextPair = randomProvider.nextPairColors()
        score = 0
        combo = 0
        isPaused = false
        isGameOver = false
        clearedLastStep = 0
        cascadesLastStep = 0
        undoUsed = false
        undoSnapshot = null
        randomProvider.restore(SeededRandomProvider(seed).stateToken())
        nextPair = randomProvider.nextPairColors()
        spawnPair()
    }

    fun undo(): GameSnapshot {
        if (undoUsed) return snapshot()
        val saved = undoSnapshot ?: return snapshot()
        restoreState(saved)
        undoUsed = true
        undoSnapshot = null
        return snapshot()
    }

    fun mode(): GameMode = mode

    internal fun debugSetBoard(board: Board) {
        require(board.rows == rows && board.cols == cols)
        this.board = board.toGrid()
    }

    internal fun debugForceActivePair(pair: FallingPair?) {
        activePair = pair
    }

    internal fun debugSetNextPair(pairColors: PairColors) {
        nextPair = pairColors
    }

    private fun moveHorizontal(delta: Int): GameSnapshot {
        if (isPaused || isGameOver) return snapshot()
        val pair = activePair ?: return snapshot()
        val moved = pair.copy(pivotCol = pair.pivotCol + delta)
        if (canPlace(moved)) {
            activePair = moved
        }
        return snapshot()
    }

    private fun spawnPair() {
        if (isGameOver) return

        val pair = FallingPair(
            pivotRow = 0,
            pivotCol = cols / 2,
            orientation = Orientation.UP,
            colors = nextPair,
        )

        nextPair = randomProvider.nextPairColors()
        if (canPlace(pair)) {
            activePair = pair
        } else {
            activePair = null
            isGameOver = true
        }
    }

    private fun canPlace(pair: FallingPair): Boolean {
        return pair.cells().all { cell ->
            GameRules.canPlace(board, cell.row, cell.col)
        }
    }

    private fun lockPair(pair: FallingPair) {
        for (cell in pair.cells()) {
            if (cell.row < 0 || cell.col !in 0 until cols || cell.row >= rows) {
                isGameOver = true
                activePair = null
                return
            }
            board[cell.row][cell.col] = cell.color
        }

        val cascade = GameRules.resolveCascades(board)
        clearedLastStep = cascade.clearedCells
        cascadesLastStep = cascade.waves
        combo = if (cascade.waves > 0) combo + cascade.waves else 0

        if (cascade.clearedCells > 0) {
            val comboBonus = (combo.coerceAtLeast(1) - 1) * 5
            score += cascade.clearedCells * 10 + comboBonus
        }

        activePair = null
        spawnPair()
    }

    private fun currentDropInterval(): Long {
        val speedScore = (score / 80).coerceAtMost(14)
        return (900L - speedScore * 40L).coerceAtLeast(280L)
    }

    private fun saveUndoStateIfEligible() {
        if (!undoUsed && undoSnapshot == null) {
            undoSnapshot = captureState()
        }
    }

    private fun captureState(): EngineState {
        return EngineState(
            board = board.copyGrid(),
            activePair = activePair,
            nextPair = nextPair,
            score = score,
            combo = combo,
            isPaused = isPaused,
            isGameOver = isGameOver,
            clearedLastStep = clearedLastStep,
            cascadesLastStep = cascadesLastStep,
            rngToken = randomProvider.stateToken(),
        )
    }

    private fun restoreState(state: EngineState) {
        board = state.board.copyGrid()
        activePair = state.activePair
        nextPair = state.nextPair
        score = state.score
        combo = state.combo
        isPaused = state.isPaused
        isGameOver = state.isGameOver
        clearedLastStep = state.clearedLastStep
        cascadesLastStep = state.cascadesLastStep
        randomProvider.restore(state.rngToken)
    }

    private data class EngineState(
        val board: Array<Array<CellColor?>>,
        val activePair: FallingPair?,
        val nextPair: PairColors,
        val score: Int,
        val combo: Int,
        val isPaused: Boolean,
        val isGameOver: Boolean,
        val clearedLastStep: Int,
        val cascadesLastStep: Int,
        val rngToken: Long,
    )

    private fun emptyGrid(rows: Int, cols: Int): Array<Array<CellColor?>> {
        return Array(rows) { arrayOfNulls(cols) }
    }

    private fun Array<Array<CellColor?>>.copyGrid(): Array<Array<CellColor?>> {
        return Array(size) { row -> this[row].copyOf() }
    }

    private fun toBoard(grid: Array<Array<CellColor?>>): Board {
        return Board(
            rows = rows,
            cols = cols,
            cells = List(rows) { row -> List(cols) { col -> grid[row][col] } },
        )
    }

    private fun Board.toGrid(): Array<Array<CellColor?>> {
        return Array(rows) { row ->
            Array(cols) { col -> cells[row][col] }
        }
    }
}
