package ru.betel.data.reopsitory.song.get.all

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.betel.domain.model.Song
import ru.betel.domain.repository.network.NetworkUtils
import ru.betel.domain.repository.song.get.all.GetAllSongs
import ru.betel.domain.repository.song.get.firebase.GetSongsFromFirebase
import ru.betel.domain.useCase.song.local.GetSongsFromLocalUseCase

class GetAllSongsImpl(
    private val getSongFromFirebase: GetSongsFromFirebase,
    private val getNetworkUtils: NetworkUtils,
    private val getSongsFromLocal: GetSongsFromLocalUseCase,
) : GetAllSongs {
    override fun getAllSongs(): Flow<List<Song>> = flow {
        if (getNetworkUtils.isConnectedNetwork()) {
            emit(getSongFromFirebase.getAllSongs())
        } else {
            emit(getSongsFromLocal.execute())
        }
    }
}