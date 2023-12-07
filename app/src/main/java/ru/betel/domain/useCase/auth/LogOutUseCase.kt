package ru.betel.domain.useCase.auth

import ru.betel.domain.repository.auth.FirebaseAuthRepo

class LogOutUseCase (private val authRepo: FirebaseAuthRepo) {

    fun execute() = authRepo.logOut()
}