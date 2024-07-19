package ru.betel.domain.useCase.theme

import ru.betel.domain.repository.theme.ThemeRepository

class GetThemeUseCase(private val appThemeRepository: ThemeRepository) {
    fun execute() = appThemeRepository.getTheme()
}




