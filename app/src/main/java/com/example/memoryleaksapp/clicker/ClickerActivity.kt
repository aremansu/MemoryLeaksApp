package com.example.memoryleaksapp.clicker

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.memoryleaksapp.R
import com.example.memoryleaksapp.extensions.show
import com.example.memoryleaksapp.level.LevelData
import com.example.memoryleaksapp.level.LevelRepository
import com.example.memoryleaksapp.level.LocalLevelRepository
import com.example.memoryleaksapp.main.MainActivity
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.robinhood.ticker.TickerView

private const val RESULT_TEXT_FMT = "Result:%1d"

class ClickerActivity: AppCompatActivity() {

    private var clickCount = 0
    private var level: Int? = null

    private lateinit var levelRepository: LevelRepository

    private lateinit var resultTextView: TextView
    private lateinit var tickerView: TickerView
    private lateinit var counterButton: Button
    private lateinit var linearProgressIndicator: LinearProgressIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clicker)

        levelRepository = LocalLevelRepository(this)
        level = levelRepository.getLevel()?.level ?: 0
        initViews()
        initListeners()
    }

    private fun initViews() {
        resultTextView = findViewById(R.id.activity_clicker_result)
        tickerView = findViewById(R.id.activity_clicker_ticker_result)
        counterButton = findViewById(R.id.activity_clicker_button)
        linearProgressIndicator = findViewById(R.id.activity_clicker_linear_progress_indicator)

        tickerView.animationDuration = 350
        tickerView.animationInterpolator = OvershootInterpolator()
        tickerView.setPreferredScrollingDirection(TickerView.ScrollingDirection.UP)
        tickerView.setCharacterLists()
    }

    private fun initListeners() {
        counterButton.setOnClickListener {
            if (!counterButton.isEnabled) return@setOnClickListener
            clickCount = clickCount.inc()
            tickerView.text = clickCount.toString()

            if (clickCount % 5 == 0) {
                linearProgressIndicator.progress = clickCount*2
            }

            if (clickCount == 50) {
                val level = level?.inc() ?: 0
                levelRepository.saveLevel(
                    LevelData(
                        level = level,
                        rank = "\nLeshy, The Scrybe of Beasts"
                    )
                )

                MainActivity.levelUpDialog?.invoke(levelRepository)
                finish()
            }
        }
    }
}