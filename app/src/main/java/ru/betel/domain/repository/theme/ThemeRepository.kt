package ru.betel.domain.repository.theme

import androidx.compose.runtime.snapshots.SnapshotStateList
import ru.betel.domain.model.ui.AppTheme
import ru.betel.domain.model.ui.ThemeMode

interface ThemeRepository {
    fun getTheme(): AppTheme

    fun setTheme(index: Int)

    fun getThemeList(): SnapshotStateList<ThemeMode>
}