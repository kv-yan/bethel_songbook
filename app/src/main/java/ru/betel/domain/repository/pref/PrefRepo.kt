package ru.betel.domain.repository.pref

interface PrefRepo {
    suspend fun setPerformerName(name: String)

    suspend fun getPerformerName(): String
}