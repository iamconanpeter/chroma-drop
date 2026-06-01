package com.iamconanpeter.chromadrop.game

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class GameLoopController(
    private val scope: CoroutineScope,
    private val intervalProviderMs: () -> Long,
    private val onTick: () -> Unit,
) {
    private var loopJob: Job? = null

    fun start() {
        if (loopJob != null) return
        loopJob = scope.launch {
            while (isActive) {
                delay(intervalProviderMs())
                onTick()
            }
        }
    }

    fun pause() {
        loopJob?.cancel()
        loopJob = null
    }

    fun stop() = pause()

    fun isRunning(): Boolean = loopJob != null
}
