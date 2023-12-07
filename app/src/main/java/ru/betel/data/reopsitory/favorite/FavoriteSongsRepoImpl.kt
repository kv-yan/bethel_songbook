package ru.betel.data.reopsitory.favorite

import kotlinx.coroutines.flow.Flow
import ru.betel.domain.dao.FavoriteSongsDao
import ru.betel.domain.model.entity.FavoriteSongsEntity
import ru.betel.domain.repository.favorite.FavoriteSongsRepo

class FavoriteSongsRepoImpl(private val favSongDao: FavoriteSongsDao) : FavoriteSongsRepo {
    override fun getFavoriteSongs(): Flow<List<FavoriteSongsEntity>> =
        favSongDao.getAllSongFavorites()

    override suspend fun insertFavoriteSongs(favoriteSongsEntity: FavoriteSongsEntity) {
        favSongDao.insertFavoriteSong(favoriteSongsEntity)
    }

    override suspend fun deleteFavoriteSongsByTitle(favoriteSongsEntity: FavoriteSongsEntity) {
        favSongDao.deleteFavoriteSong(favoriteSongsEntity)
    }
}