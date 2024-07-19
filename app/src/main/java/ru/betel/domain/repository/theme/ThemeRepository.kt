package ru.betel.domain.repository.theme

import ru.betel.domain.model.ui.AppTheme

interface ThemeRepository {
    fun getTheme(): AppTheme

    fun setTheme(index: Int)
}