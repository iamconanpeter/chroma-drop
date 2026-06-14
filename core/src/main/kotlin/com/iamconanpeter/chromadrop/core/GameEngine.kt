package com.iamconanpeter.chromadrop.core

import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

/**
 * Simple color enum for the game.
 */
enum class GameColor { RED, GREEN, BLUE, YELLOW, PURPLE }

/**
 * Immutable snapshot of the game state.
 */
data class GameState(
    val targetColors: List<GameColor>,
    val slotColors: List<GameColor>,
    val currentBall: GameColor?,
    val score: Int,
    val lives: Int,
    val combo: Int
)

/**
 * Core engine – pure Kotlin, no Android dependencies.
 */
class GameEngine(
    private val config: GameConfig = GameConfig()
) {
    private val _state = MutableStateFlow(initialState())
    val state: StateFlow<GameState> = _state

    private var fallingBall: GameColor? = null
    private var ballTimerMs = 0L

    private fun initialState() = GameState(
        targetColors = config.randomTarget(),
        slotColors = emptyList(),
        currentBall = null,
        score = 0,
        lives = config.maxLives,
        combo = 0
    )

    /** Starts the game loop – caller must run in a coroutine. */
    suspend fun start() {
        while (isActive && _state.value.lives > 0) {
            tick(16L) // approx 60 FPS
            delay(16L)
        }
    }

    private suspend fun tick(deltaMs: Long) {
        // Spawn a new ball if none present
        if (fallingBall == null) {
            fallingBall = GameColor.values().random()
            ballTimerMs = 0L
            emitState(currentBall = fallingBall)
        } else {
            // Increment timer; if exceeds drop time, auto‑lock (miss)
            ballTimerMs += deltaMs
            if (ballTimerMs >= config.dropTimeMs) {
                // missed lock – penalize
                handleLock(matched = false)
            }
        }
    }

    /** Called by UI when player taps to lock the current ball. */
    fun lockCurrentBall() {
        val ball = fallingBall ?: return
        // Simple match logic – if ball color equals first target then success
        val matched = ball == _state.value.targetColors.firstOrNull()
        handleLock(matched)
    }

    private fun handleLock(matched: Boolean) {
        val old = _state.value
        if (matched) {
            // success – add to slot, shift target, increase score/combo
            val newSlot = old.slotColors + fallingBall!!
            val newScore = old.score + (10 * (old.combo + 1))
            val newCombo = old.combo + 1
            val newTargets = old.targetColors.drop(1) + config.randomColor()
            _state.value = old.copy(
                targetColors = newTargets,
                slotColors = newSlot,
                score = newScore,
                combo = newCombo,
                currentBall = null
            )
        } else {
            // failure – lose a life, reset combo
            val newLives = old.lives - 1
            _state.value = old.copy(
                lives = newLives,
                combo = 0,
                currentBall = null
            )
        }
        // Reset falling ball
        fallingBall = null
        ballTimerMs = 0L
    }

    private fun emitState(currentBall: GameColor?) {
        val old = _state.value
        _state.value = old.copy(currentBall = currentBall)
    }
}

/** Configuration parameters for the engine. */
class GameConfig(
    val maxLives: Int = 3,
    val dropTimeMs: Long = 3000L,
    val targetLength: Int = 3
) {
    fun randomColor(): GameColor = GameColor.values().random()
    fun randomTarget(): List<GameColor> = List(targetLength) { randomColor() }
}
