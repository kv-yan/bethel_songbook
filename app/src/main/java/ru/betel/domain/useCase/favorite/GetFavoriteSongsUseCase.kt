package ru.betel.domain.useCase.favorite

import kotlinx.coroutines.flow.flow
import ru.betel.data.extensions.toSong
import ru.betel.domain.model.Song
import ru.betel.domain.repository.favorite.FavoriteSongsRepo

class GetFavoriteSongsUseCase(private val favoriteSongsRepo: FavoriteSongsRepo) {
    suspend fun execute() = flow<List<Song>> {
        favoriteSongsRepo.getFavoriteSongs().collect{
         emit(it.toSong())
        }
    }
}