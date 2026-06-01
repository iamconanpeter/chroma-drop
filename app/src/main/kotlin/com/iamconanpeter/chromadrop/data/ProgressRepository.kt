package com.iamconanpeter.chromadrop.data

import com.iamconanpeter.chromadrop.data.model.DailyRecord
import com.iamconanpeter.chromadrop.data.model.PlayerStats
import com.iamconanpeter.chromadrop.data.model.Settings
import kotlinx.coroutines.flow.Flow

interface ProgressRepository {
    val playerStats: Flow<PlayerStats>
    val settings: Flow<Settings>

    fun dailyRecord(dateKey: String): Flow<DailyRecord?>

    suspend fun saveBestScore(score: Int)
    suspend fun saveDailyRecord(record: DailyRecord)
    suspend fun saveSettings(settings: Settings)
}
