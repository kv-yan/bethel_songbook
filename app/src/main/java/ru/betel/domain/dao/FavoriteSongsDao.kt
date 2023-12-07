package ru.betel.domain.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.betel.domain.model.entity.FavoriteSongsEntity

@Dao
interface FavoriteSongsDao {
    @Query("SELECT * FROM FAVORITE_SONGS_ENTITY")
    fun getAllSongFavorites(): Flow<List<FavoriteSongsEntity>>

    @Insert
    suspend fun insertFavoriteSong(songTemplate: FavoriteSongsEntity)

    @Delete
    suspend fun deleteFavoriteSong(songTemplate: FavoriteSongsEntity)
}