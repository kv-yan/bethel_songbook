package ru.betel.domain.useCase.pref.performer_name

import ru.betel.domain.repository.pref.PrefRepo

class SetPerformerNameUseCase(val repo: PrefRepo) {
    suspend fun execute(performerName: String) = repo.setPerformerName(performerName)
}
