package ru.betel.data.reopsitory.theme

import android.content.SharedPreferences
import ru.betel.domain.model.ui.AppTheme
import ru.betel.domain.repository.theme.ThemeRepository

class ThemeRepositoryImpl(private val sharedPreferences: SharedPreferences) : ThemeRepository {
    override fun getTheme(): AppTheme {
       val index =  sharedPreferences.getInt("theme", 1)
       return when (index) {
            0 -> {AppTheme.PRIMARY}
            1 -> {AppTheme.GRAY}
            else -> {AppTheme.DARK}
        }
    }

    override fun setTheme(index: Int) {
        sharedPreferences.edit().putInt("theme", index).apply()
    }
}