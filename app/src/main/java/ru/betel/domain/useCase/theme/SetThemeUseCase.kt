package ru.betel.domain.useCase.theme

import ru.betel.domain.repository.theme.ThemeRepository

class SetThemeUseCase(private val appThemeRepository: ThemeRepository) {
    fun execute(index: Int) = appThemeRepository.setTheme(index)
}