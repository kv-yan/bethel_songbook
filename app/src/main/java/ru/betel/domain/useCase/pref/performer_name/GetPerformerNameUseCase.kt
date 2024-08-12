package ru.betel.domain.useCase.pref.performer_name

import ru.betel.domain.repository.pref.PrefRepo

class GetPerformerNameUseCase(val repo: PrefRepo) {
    suspend fun execute(): String = repo.getPerformerName()
}
