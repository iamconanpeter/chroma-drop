package com.iamconanpeter.chromadrop.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.iamconanpeter.chromadrop.data.ProgressRepository
import com.iamconanpeter.chromadrop.data.model.DailyRecord
import com.iamconanpeter.chromadrop.data.model.Settings
import com.iamconanpeter.chromadrop.engine.GameEngine
import com.iamconanpeter.chromadrop.engine.GameMode
import com.iamconanpeter.chromadrop.engine.SeedFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class GameViewModel(
    private val repository: ProgressRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var engine: GameEngine = GameEngine(
        seed = SeedFactory.endless(System.currentTimeMillis()),
        mode = GameMode.ENDLESS,
    )
    private var gameLoopController = GameLoopController(
        scope = viewModelScope,
        intervalProviderMs = { currentSnapshot().dropIntervalMs },
        onTick = { onTick() },
    )
    private var dailyRecordJob: Job? = null

    init {
        viewModelScope.launch {
            repository.playerStats.collect { stats ->
                _uiState.update { state -> state.copy(playerStats = stats) }
            }
        }
        viewModelScope.launch {
            repository.settings.collect { settings ->
                _uiState.update { state -> state.copy(settings = settings) }
            }
        }
    }

    fun startEndless() {
        val seed = SeedFactory.endless(System.currentTimeMillis())
        startGame(mode = GameMode.ENDLESS, seed = seed, dateKey = "")
    }

    fun startDaily(date: LocalDate = LocalDate.now()) {
        val dateKey = date.toString()
        startGame(mode = GameMode.DAILY, seed = SeedFactory.daily(date), dateKey = dateKey)
        observeDailyRecord(dateKey)
    }

    fun moveLeft() = applyEngineAction { moveLeft() }
    fun moveRight() = applyEngineAction { moveRight() }
    fun rotate() = applyEngineAction { rotateClockwise() }
    fun softDrop() = applyEngineAction { softDrop() }
    fun hardDrop() = applyEngineAction { hardDrop() }
    fun undo() = applyEngineAction { undo() }

    fun pauseOrResume() {
        val snapshot = currentSnapshot()
        if (snapshot.isPaused) {
            engine.resume()
            gameLoopController.start()
        } else {
            engine.pause()
            gameLoopController.pause()
        }
        syncSnapshot()
    }

    fun openHome() {
        gameLoopController.stop()
        _uiState.update { state ->
            state.copy(screen = Screen.HOME)
        }
    }

    fun openSettings() {
        _uiState.update { state -> state.copy(screen = Screen.SETTINGS) }
    }

    fun openDailySummary() {
        _uiState.update { state -> state.copy(screen = Screen.DAILY_SUMMARY) }
    }

    fun saveSettings(settings: Settings) {
        viewModelScope.launch {
            repository.saveSettings(settings)
        }
    }

    fun retryCurrentMode() {
        val state = _uiState.value
        if (state.mode == GameMode.DAILY) {
            val date = LocalDate.parse(state.dailyKey)
            startDaily(date)
        } else {
            startEndless()
        }
    }

    private fun startGame(mode: GameMode, seed: Long, dateKey: String) {
        gameLoopController.stop()
        engine = GameEngine(seed = seed, mode = mode)
        syncSnapshot(screen = Screen.GAME, mode = mode, dateKey = dateKey)
        gameLoopController = GameLoopController(
            scope = viewModelScope,
            intervalProviderMs = { currentSnapshot().dropIntervalMs },
            onTick = { onTick() },
        )
        gameLoopController.start()
    }

    private fun observeDailyRecord(dateKey: String) {
        dailyRecordJob?.cancel()
        dailyRecordJob = viewModelScope.launch {
            repository.dailyRecord(dateKey).collect { record ->
                _uiState.update { state ->
                    state.copy(dailyRecord = record)
                }
            }
        }
    }

    private fun applyEngineAction(action: GameEngine.() -> Unit) {
        action(engine)
        syncSnapshot()
        maybeFinalizeRun()
    }

    private fun onTick() {
        engine.tick()
        syncSnapshot()
        maybeFinalizeRun()
    }

    private fun syncSnapshot(
        screen: Screen? = null,
        mode: GameMode? = null,
        dateKey: String? = null,
    ) {
        val snapshot = engine.snapshot()
        _uiState.update { state ->
            state.copy(
                screen = screen ?: state.screen,
                mode = mode ?: state.mode,
                dailyKey = dateKey ?: state.dailyKey,
                snapshot = snapshot,
            )
        }
    }

    private fun maybeFinalizeRun() {
        val snapshot = currentSnapshot()
        if (!snapshot.isGameOver) return

        gameLoopController.stop()
        _uiState.update { state -> state.copy(screen = Screen.GAME_OVER) }

        viewModelScope.launch {
            repository.saveBestScore(snapshot.score)
            if (_uiState.value.mode == GameMode.DAILY && _uiState.value.dailyKey.isNotBlank()) {
                val stars = when {
                    snapshot.score >= 800 -> 3
                    snapshot.score >= 450 -> 2
                    snapshot.score >= 200 -> 1
                    else -> 0
                }
                repository.saveDailyRecord(
                    DailyRecord(
                        dateKey = _uiState.value.dailyKey,
                        score = snapshot.score,
                        stars = stars,
                    )
                )
            }
        }
    }

    private fun currentSnapshot() = engine.snapshot()

    class Factory(
        private val repository: ProgressRepository,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return GameViewModel(repository) as T
        }
    }
}
