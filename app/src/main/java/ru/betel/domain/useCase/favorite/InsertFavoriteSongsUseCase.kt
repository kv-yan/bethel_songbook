package ru.betel.domain.useCase.favorite

import ru.betel.domain.model.entity.FavoriteSongsEntity
import ru.betel.domain.repository.favorite.FavoriteSongsRepo

class InsertFavoriteSongsUseCase(private val favoriteSongsRepo: FavoriteSongsRepo) {
    suspend fun execute(songsEntity: FavoriteSongsEntity) = favoriteSongsRepo.insertFavoriteSongs(songsEntity)
}