package com.iamconanpeter.chromadrop.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.iamconanpeter.chromadrop.data.model.DailyRecord
import com.iamconanpeter.chromadrop.data.model.PlayerStats
import com.iamconanpeter.chromadrop.data.model.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.progressDataStore: DataStore<Preferences> by preferencesDataStore(name = "chroma_drop_progress")

class DataStoreProgressRepository(
    private val context: Context,
) : ProgressRepository {
    private object Keys {
        val bestScore = intPreferencesKey("best_score")
        val audioEnabled = booleanPreferencesKey("audio_enabled")
        val hapticsEnabled = booleanPreferencesKey("haptics_enabled")
        val colorblindMode = booleanPreferencesKey("colorblind_mode")

        fun dailyKey(dateKey: String) = stringPreferencesKey("daily_$dateKey")
    }

    override val playerStats: Flow<PlayerStats> = context.progressDataStore.data.map { pref ->
        PlayerStats(bestScore = pref[Keys.bestScore] ?: 0)
    }

    override val settings: Flow<Settings> = context.progressDataStore.data.map { pref ->
        Settings(
            audioEnabled = pref[Keys.audioEnabled] ?: true,
            hapticsEnabled = pref[Keys.hapticsEnabled] ?: true,
            colorblindMode = pref[Keys.colorblindMode] ?: false,
        )
    }

    override fun dailyRecord(dateKey: String): Flow<DailyRecord?> {
        return context.progressDataStore.data.map { pref ->
            pref[Keys.dailyKey(dateKey)]?.toDailyRecord(dateKey)
        }
    }

    override suspend fun saveBestScore(score: Int) {
        context.progressDataStore.edit { pref ->
            if (score > (pref[Keys.bestScore] ?: 0)) {
                pref[Keys.bestScore] = score
            }
        }
    }

    override suspend fun saveDailyRecord(record: DailyRecord) {
        context.progressDataStore.edit { pref ->
            val key = Keys.dailyKey(record.dateKey)
            val existing = pref[key]?.toDailyRecord(record.dateKey)
            if (existing == null || record.score > existing.score) {
                pref[key] = "${record.score}|${record.stars}"
            }
        }
    }

    override suspend fun saveSettings(settings: Settings) {
        context.progressDataStore.edit { pref ->
            pref[Keys.audioEnabled] = settings.audioEnabled
            pref[Keys.hapticsEnabled] = settings.hapticsEnabled
            pref[Keys.colorblindMode] = settings.colorblindMode
        }
    }

    private fun String.toDailyRecord(dateKey: String): DailyRecord? {
        val parts = split('|')
        if (parts.size != 2) return null
        val score = parts[0].toIntOrNull() ?: return null
        val stars = parts[1].toIntOrNull() ?: return null
        return DailyRecord(dateKey = dateKey, score = score, stars = stars)
    }
}
