package ru.betel.domain.useCase.auth

import ru.betel.domain.repository.auth.FirebaseAuthRepo
import java.util.concurrent.Flow

class LogInUseCase(private val authRepo: FirebaseAuthRepo) {
    suspend fun execute(email: String, password: String) = authRepo.logIn(
        email = email,
        password = password
    )
}