package com.example.chromadrop

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GameViewModelTest {
    @Test
    fun onRotateLeft_updatesRotationCorrectly() = runTest {
        val vm = GameViewModel()
        val initial = vm.gameState.value.rotation
        vm.onRotateLeft()
        assertEquals((initial + 3) % 4, vm.gameState.value.rotation)
    }

    @Test
    fun onDrop_incrementsScore() = runTest {
        val vm = GameViewModel()
        val initial = vm.gameState.value.score
        vm.onDrop()
        assertTrue(vm.gameState.value.score > initial)
    }
}
