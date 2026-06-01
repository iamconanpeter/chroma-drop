package com.iamconanpeter.chromadrop.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iamconanpeter.chromadrop.data.DataStoreProgressRepository
import com.iamconanpeter.chromadrop.game.GameViewModel
import com.iamconanpeter.chromadrop.game.Screen
import com.iamconanpeter.chromadrop.ui.screens.DailySummaryScreen
import com.iamconanpeter.chromadrop.ui.screens.GameOverScreen
import com.iamconanpeter.chromadrop.ui.screens.GameScreen
import com.iamconanpeter.chromadrop.ui.screens.HomeScreen
import com.iamconanpeter.chromadrop.ui.screens.SettingsScreen

@Composable
fun ChromaDropApp(
    repository: DataStoreProgressRepository,
) {
    val viewModel: GameViewModel = viewModel(factory = GameViewModel.Factory(repository))
    val state by viewModel.uiState.collectAsState()

    when (state.screen) {
        Screen.HOME -> HomeScreen(
            stats = state.playerStats,
            onStartEndless = viewModel::startEndless,
            onStartDaily = viewModel::startDaily,
            onOpenSettings = viewModel::openSettings,
            onOpenDailySummary = viewModel::openDailySummary,
        )

        Screen.GAME -> {
            val snapshot = state.snapshot ?: return
            GameScreen(
                snapshot = snapshot,
                onMoveLeft = viewModel::moveLeft,
                onMoveRight = viewModel::moveRight,
                onRotate = viewModel::rotate,
                onSoftDrop = viewModel::softDrop,
                onHardDrop = viewModel::hardDrop,
                onUndo = viewModel::undo,
                onPauseResume = viewModel::pauseOrResume,
                onExit = viewModel::openHome,
            )
        }

        Screen.GAME_OVER -> GameOverScreen(
            score = state.snapshot?.score ?: 0,
            onRetry = viewModel::retryCurrentMode,
            onHome = viewModel::openHome,
        )

        Screen.SETTINGS -> SettingsScreen(
            settings = state.settings,
            onSave = viewModel::saveSettings,
            onBack = viewModel::openHome,
        )

        Screen.DAILY_SUMMARY -> DailySummaryScreen(
            record = state.dailyRecord,
            onBack = viewModel::openHome,
        )
    }
}
