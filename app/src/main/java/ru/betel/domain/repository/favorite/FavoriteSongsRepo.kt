package ru.betel.domain.repository.favorite

import kotlinx.coroutines.flow.Flow
import ru.betel.domain.model.entity.FavoriteSongsEntity

interface FavoriteSongsRepo {
    fun getFavoriteSongs(): Flow<List<FavoriteSongsEntity>>

    suspend fun insertFavoriteSongs(favoriteSongsEntity: FavoriteSongsEntity)

    suspend fun deleteFavoriteSongsByTitle(favoriteSongsEntity: FavoriteSongsEntity)
}