package com.example.memoryleaksapp.level

import android.content.Context
import android.content.SharedPreferences

private const val LOCAL_LEVEL_SHARED_PREFS_NAME = "LocalLevelSharedPrefs"
private const val LEVEL_SHARED_PREFS_KEY = "level"
private const val RANK_SHARED_PREFS_KEY = "rank"

class LocalLevelDataSource(context: Context) {

    private val localContext = context

    private val sharedPreferences = localContext.getSharedPreferences(
        LOCAL_LEVEL_SHARED_PREFS_NAME,
        Context.MODE_PRIVATE
    )

    fun saveLevelLocal(levelData: LevelData) {
        sharedPreferences
            .edit()
            .putInt(LEVEL_SHARED_PREFS_KEY, levelData.level)
            .putString(RANK_SHARED_PREFS_KEY, levelData.rank)
            .apply()
    }

    fun getLevelLocal(): LevelData? {
        val level = sharedPreferences.getIntOrNull(LEVEL_SHARED_PREFS_KEY)
            ?: return null
        val rank = sharedPreferences.getString(RANK_SHARED_PREFS_KEY, null)
            ?: return null

        return LevelData(level, rank)
    }

    private fun SharedPreferences.getIntOrNull(key: String): Int? {
        val value = this.getInt(key, -1)

        return if (value == -1) {
            null
        } else {
            value
        }
    }
}