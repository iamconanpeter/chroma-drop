package com.iamconanpeter.chromadrop.core

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class GameEngineTest {
    @Test
    fun testLockSuccessIncrementsScoreAndCombo() = runBlocking {
        val engine = GameEngine()
        val stateList = mutableListOf<GameState>()
        engine.state.subscribe { stateList.add(it) }
        // Force current ball to match first target
        engine.lockCurrentBall() // no ball, should ignore
        // Manually set falling ball to target color
        engine.start() // start loop to spawn ball
        // Wait a bit and get current ball
        kotlin.coroutines.delay(20)
        val current = engine.state.value.currentBall
        if (current == null) fail("No current ball spawned")
        // Lock it
        engine.lockCurrentBall()
        val after = engine.state.value
        assertEquals(10, after.score) // 10 * combo 1
        assertEquals(1, after.combo)
    }
}
