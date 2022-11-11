package com.example.memoryleaksapp.level

interface LevelRepository {

    fun getLevel(): LevelData?

    fun saveLevel(levelData: LevelData)
}