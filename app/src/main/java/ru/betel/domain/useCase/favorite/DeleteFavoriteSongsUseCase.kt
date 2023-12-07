package ru.betel.domain.useCase.favorite

import ru.betel.domain.model.entity.FavoriteSongsEntity
import ru.betel.domain.repository.favorite.FavoriteSongsRepo

class DeleteFavoriteSongsUseCase(private val favoriteSongsRepo: FavoriteSongsRepo) {
    suspend fun execute(favoriteSongsEntity: FavoriteSongsEntity) =
        favoriteSongsRepo.deleteFavoriteSongsByTitle(favoriteSongsEntity)
}