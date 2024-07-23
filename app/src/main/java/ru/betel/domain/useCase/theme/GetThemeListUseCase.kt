package ru.betel.domain.useCase.theme

import ru.betel.domain.repository.theme.ThemeRepository

class GetThemeListUseCase(private val appThemeRepository: ThemeRepository) {
    fun execute() = appThemeRepository.getThemeList()
}