package ru.betel.domain.useCase.sync.song

import ru.betel.domain.repository.song.sync.SyncSongsFromFBToLocalStorage

class SyncSongFromFbToLocalStorageUseCase(private val syncSongsFromFBToLocalStorage: SyncSongsFromFBToLocalStorage) {
    suspend fun execute() {
        syncSongsFromFBToLocalStorage.syncData()
    }
}