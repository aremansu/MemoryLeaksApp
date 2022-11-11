package com.example.memoryleaksapp.handler

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.memoryleaksapp.R
import com.example.memoryleaksapp.extensions.gone
import com.example.memoryleaksapp.extensions.show

private const val DELAY_VALUE = 10000L

class HandlerActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var button: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handler)
        textView = findViewById(R.id.handler_title)
        progressBar = findViewById(R.id.handler_progress)
        button = findViewById(R.id.handler_load)
        button.setOnClickListener {
            startLongLoad()
        }
    }

    private fun startLongLoad() {
        Toast.makeText(textView.context, getText(R.string.loading), Toast.LENGTH_LONG).show()
        progressBar.show()

        Handler(mainLooper).postDelayed({
            Toast.makeText(this, getText(R.string.done), Toast.LENGTH_LONG).show()
            progressBar.gone()
        }, DELAY_VALUE)
    }
}