package ru.betel.domain.dao

import androidx.room.*
import ru.betel.domain.model.entity.SongEntity

@Dao
interface SongDao {
    @Query("SELECT * FROM song")
    suspend fun getAllSong(): List<SongEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSong(songTemplate: SongEntity)

    @Update
    suspend fun updateSong(songTemplate: SongEntity)

    @Delete
    suspend fun deleteSong(songTemplate: SongEntity)

    @Query("DELETE FROM song")
    suspend fun cleareAllSongs()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSongs(songTemplates: List<SongEntity>)
}

