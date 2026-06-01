package com.iamconanpeter.chromadrop.game

import com.iamconanpeter.chromadrop.data.model.DailyRecord
import com.iamconanpeter.chromadrop.data.model.PlayerStats
import com.iamconanpeter.chromadrop.data.model.Settings
import com.iamconanpeter.chromadrop.engine.GameMode
import com.iamconanpeter.chromadrop.engine.model.GameSnapshot

enum class Screen {
    HOME,
    GAME,
    GAME_OVER,
    SETTINGS,
    DAILY_SUMMARY,
}

data class GameUiState(
    val screen: Screen = Screen.HOME,
    val mode: GameMode = GameMode.ENDLESS,
    val snapshot: GameSnapshot? = null,
    val playerStats: PlayerStats = PlayerStats(),
    val settings: Settings = Settings(),
    val dailyRecord: DailyRecord? = null,
    val dailyKey: String = "",
)
