package ru.betel.domain.useCase.auth

import ru.betel.domain.repository.auth.FirebaseAuthRepo

class CheckUserLoginStatusUseCase (private val authRepo: FirebaseAuthRepo) {

    fun execute() = authRepo.isSignIn()
}