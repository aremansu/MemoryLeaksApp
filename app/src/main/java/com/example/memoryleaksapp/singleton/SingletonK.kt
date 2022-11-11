package com.example.memoryleaksapp.singleton

import com.example.memoryleaksapp.authentication.User
import com.example.memoryleaksapp.authentication.UserAuthUseCase

class SingletonK private constructor(
    private val userAuthUseCase: UserAuthUseCase
) {

    var user: User? = null
        get() {
        return if (field == null) {
            userAuthUseCase.getUser()
        } else {
            field
        }
    }
    set(value) {
        value?.let { userAuthUseCase.saveUser(it) }
            ?: userAuthUseCase.deleteUser()
    }

    companion object {

        val instance: SingletonK?
            get() = sInstance?.let {
                return it
            }// ?: throw IllegalArgumentException("ÆÆÆÆÆÆÆÆÆÆÆÆÆÆÆÆÆÆÆÆÆÆÆÆÆÆÆÆÆÆ")

        private var sInstance: SingletonK? = null

        fun createInstance(authenticatorUseCase: UserAuthUseCase) {
            sInstance = SingletonK(authenticatorUseCase)
        }

        fun destroyInstance() {
            sInstance = null
        }
    }
}