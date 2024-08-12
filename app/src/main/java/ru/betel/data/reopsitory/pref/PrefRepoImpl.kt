package ru.betel.data.reopsitory.pref

import android.content.SharedPreferences
import ru.betel.domain.repository.pref.PrefRepo

class PrefRepoImpl(val sharedPreferences: SharedPreferences) : PrefRepo {
    override suspend fun setPerformerName(name: String) {
        sharedPreferences.edit().putString("performerName", name).apply()
    }

    override suspend fun getPerformerName(): String =
        sharedPreferences.getString("performerName", "") ?: ""
}