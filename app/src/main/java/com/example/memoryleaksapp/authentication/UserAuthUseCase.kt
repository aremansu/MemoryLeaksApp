package com.example.memoryleaksapp.authentication

interface UserAuthUseCase {

    fun saveUser(user: User)

    fun deleteUser()

    fun getUser(): User?
}