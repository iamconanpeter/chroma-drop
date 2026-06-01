package com.iamconanpeter.chromadrop.engine.model

data class GameSnapshot(
    val board: Board,
    val activePair: FallingPair?,
    val nextPair: PairColors,
    val score: Int,
    val combo: Int,
    val clearedLastStep: Int,
    val cascadeWavesLastStep: Int,
    val dropIntervalMs: Long,
    val isGameOver: Boolean,
    val isPaused: Boolean,
    val undoAvailable: Boolean,
)
