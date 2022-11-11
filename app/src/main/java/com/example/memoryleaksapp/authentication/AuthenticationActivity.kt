package com.example.memoryleaksapp.authentication

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.memoryleaksapp.main.MainActivity
import com.example.memoryleaksapp.R
import com.example.memoryleaksapp.singleton.SingletonK

class AuthenticationActivity: AppCompatActivity() {

    companion object {
        var logError: ((String, Throwable) -> Unit)? = null
    }

    private val textWatcher by lazy { initTextWatcher() }

    private var usernameEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var continueButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        val authenticator: UserAuthUseCase = LocalUserAuthUseCase(applicationContext)
        SingletonK.createInstance(authenticator)

        if (SingletonK.instance?.user == null) {
            initViews()
            setUpListeners()
        } else {
            openMainActivity()
        }

        logError = { tag, msg ->
            Log.e(
                tag,
                msg.message ?: "w a t"
            )
        }
    }

    private fun initViews() {
        usernameEditText = findViewById(R.id.username_edittext)
        passwordEditText = findViewById(R.id.password_edittext)
        continueButton = findViewById(R.id.continue_button)
    }

    private fun setUpListeners() {
        val a = CustomTextWatcher()
        usernameEditText?.addTextChangedListener(a)
        passwordEditText?.addTextChangedListener(textWatcher)

        continueButton?.setOnClickListener {
            val user = getEnteredUserData() ?: return@setOnClickListener
            SingletonK.instance?.user = user
            openMainActivity()
        }
    }

    private fun openMainActivity() {
        val mainActivityIntent = Intent(this, MainActivity::class.java)
        startActivity(mainActivityIntent)
        finish()
    }

    private fun enableContinueButton() {
        continueButton?.isEnabled = true
        continueButton?.background =
            ContextCompat.getDrawable(this, R.drawable.bg_button_enabled)
        continueButton?.setTextColor(ContextCompat.getColor(this, R.color.white))
    }

    private fun disableContinueButton() {
        continueButton?.isEnabled = false
        continueButton?.background =
            ContextCompat.getDrawable(this, R.drawable.bg_button_disabled)
        continueButton?.setTextColor(ContextCompat.getColor(this, R.color.grey))
    }

    private fun getEnteredUserData(): User? {
        val username = usernameEditText?.text.toStringOrNull() ?: return null
        val password = passwordEditText?.text.toStringOrNull() ?: return null

        return User(username, password)
    }

    inner class CustomTextWatcher: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s.isNullOrEmpty())

            if (s.isNullOrEmpty()) {
                disableContinueButton()
            } else {
                enableContinueButton()
            }
        }

        override fun afterTextChanged(s: Editable?) = Unit
    }

    private fun initTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val isUsernameEntered = !usernameEditText?.text.isNullOrBlank()
                val isPasswordEntered = !passwordEditText?.text.isNullOrBlank()

                if (isUsernameEntered && isPasswordEntered) {
                    enableContinueButton()
                } else {
                    disableContinueButton()
                }
            }

            override fun afterTextChanged(s: Editable?) = Unit
        }
    }
}

fun Editable?.toStringOrNull(): String? = this?.toString()