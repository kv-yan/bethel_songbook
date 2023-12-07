package ru.betel.domain.repository.song.sync

interface SyncSongsFromFBToLocalStorage {
    suspend fun syncData()
}