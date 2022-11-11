package com.example.memoryleaksapp.level

import android.content.Context

class LocalLevelRepository(context: Context): LevelRepository {

    private val mContext = context
    private val dataSource = LocalLevelDataSource(mContext)

    override fun getLevel(): LevelData? = dataSource.getLevelLocal()

    override fun saveLevel(levelData: LevelData) = dataSource.saveLevelLocal(levelData)
}