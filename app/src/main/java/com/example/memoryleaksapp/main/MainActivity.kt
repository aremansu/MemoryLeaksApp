package com.example.memoryleaksapp.main

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.memoryleaksapp.R
import com.example.memoryleaksapp.authentication.AuthenticationActivity
import com.example.memoryleaksapp.clicker.ClickerActivity
import com.example.memoryleaksapp.handler.HandlerActivity
import com.example.memoryleaksapp.level.LevelData
import com.example.memoryleaksapp.level.LevelRepository
import com.example.memoryleaksapp.singleton.SingletonK

private const val TITLE_FMT = "Welcome, %1s"

class MainActivity : AppCompatActivity() {

    companion object {
        var levelUpDialog: ((LevelRepository) -> AlertDialog)? = null
        var logError: ((Throwable) -> Unit)? = null
    }

    private lateinit var usernameTextView: TextView
    private lateinit var longRunningTaskButton: Button
    private lateinit var calculatorButton: Button
    private lateinit var logoutButton: Button

    private var localLevelRepository: LevelData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        levelUpDialog = { showLevelUpDialog(it) }

        if (SingletonK.instance?.user == null) {
            logError = {
                Log.e(
                    this::class.simpleName,
                    it.message ?: "L E A K"
                )
            }
            navigateToAuthActivity()
            finish()
        }
        val username = TITLE_FMT.format(SingletonK.instance?.user?.username)
        usernameTextView = findViewById(R.id.activity_main_username)
        usernameTextView.text = username

        longRunningTaskButton = findViewById(R.id.second_button)
        longRunningTaskButton.setOnClickListener { navigateToHandlerActivity() }

        calculatorButton = findViewById(R.id.third_button)
        calculatorButton.setOnClickListener { navigateToCalculationsActivity() }

        logoutButton = findViewById(R.id.fourth_button)
        logoutButton.setOnClickListener {
            logOut()
            navigateToAuthActivity()
            finish()
        }
    }

    private fun navigateToHandlerActivity() {
        val intent = Intent(this, HandlerActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToCalculationsActivity() {
        val intent = Intent(this, ClickerActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToAuthActivity() {
        val authActivity = Intent(this, AuthenticationActivity::class.java)
        startActivity(authActivity)
    }

    private fun showLevelUpDialog(levelRepository: LevelRepository) =
        AlertDialog.Builder(this).apply {
            localLevelRepository = levelRepository.getLevel()
            val levelData = levelRepository.getLevel()
            setTitle("Level up! ${levelData?.level}")
            setMessage("Nice job! Rank: ${levelData?.rank}")
            setPositiveButton("ok..") { _, _ ->
                Toast.makeText(this@MainActivity, "Done", Toast.LENGTH_SHORT).show()
            }
        }.show()

    private fun logOut() {
        val logOut = LeakyClass(this)
        logOut.logOut()
    }

    inner class LeakyClass(activity: Activity) {

        private val context: Context = activity

        fun logOut() {
            SingletonK.instance?.user = null
            Toast.makeText(context, "Logged out!", Toast.LENGTH_LONG).show()
        }
    }
}
