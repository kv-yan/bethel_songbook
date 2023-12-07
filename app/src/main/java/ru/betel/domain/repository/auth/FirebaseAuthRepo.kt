package ru.betel.domain.repository.auth

import kotlinx.coroutines.flow.Flow


interface FirebaseAuthRepo {
    suspend fun logIn(email: String, password: String): Result<Unit>

    fun logOut(): Result<Unit>

    fun isSignIn(): Flow<Boolean>
}