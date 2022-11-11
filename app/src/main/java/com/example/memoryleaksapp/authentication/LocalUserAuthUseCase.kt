package com.example.memoryleaksapp.authentication

import android.content.Context

private const val AUTHENTICATOR_SHARED_PREFS_NAME = "AuthenticationSharedPrefs"
private const val USERNAME_SHARED_PREFS_KEY = "username"
private const val PASSWORD_SHARED_PREFS_KEY = "password"

class LocalUserAuthUseCase(context: Context): UserAuthUseCase {

    private val authContext = context

    private val sharedPreferences = authContext.getSharedPreferences(
        AUTHENTICATOR_SHARED_PREFS_NAME,
        Context.MODE_PRIVATE
    )

    override fun saveUser(user: User) {
        sharedPreferences
            .edit()
            .putString(USERNAME_SHARED_PREFS_KEY, user.username)
            .putString(PASSWORD_SHARED_PREFS_KEY, user.password)
            .apply()
    }

    override fun deleteUser() {
        sharedPreferences
            .edit()
            .putString(USERNAME_SHARED_PREFS_KEY, null)
            .putString(PASSWORD_SHARED_PREFS_KEY, null)
            .apply()
    }

    override fun getUser(): User? {
        val username = sharedPreferences.getString(USERNAME_SHARED_PREFS_KEY, null)
            ?: return null
        val password = sharedPreferences.getString(PASSWORD_SHARED_PREFS_KEY, null)
            ?: return null

        return User(username, password)
    }
}