package ru.betel.data.reopsitory.theme

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import ru.betel.domain.model.ui.AppTheme
import ru.betel.domain.model.ui.ThemeMode
import ru.betel.domain.repository.theme.ThemeRepository

class ThemeRepositoryImpl(private val sharedPreferences: SharedPreferences) : ThemeRepository {
    override fun getTheme(): AppTheme {
        val index = sharedPreferences.getInt("theme", 0)
        return when (index) {
            0 -> {
                AppTheme.PRIMARY
            }

            else -> {
                AppTheme.GRAY
            }
        }
    }

    override fun getThemeList(): SnapshotStateList<ThemeMode> {
        val index = sharedPreferences.getInt("theme", 0)

        val light = ThemeMode.Light
        val dark = ThemeMode.Dark

        if (index == 0) {
            light.isSelected.value = true
            dark.isSelected.value = false
        } else {
            light.isSelected.value = false
            dark.isSelected.value = true
        }
        return mutableStateListOf(light, dark)
    }

    override fun setTheme(index: Int) {
        sharedPreferences.edit().putInt("theme", index).apply()
    }
}