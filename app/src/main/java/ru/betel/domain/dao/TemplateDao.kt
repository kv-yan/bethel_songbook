package ru.betel.domain.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.betel.domain.model.entity.SongTemplateEntity

@Dao
interface TemplateDao {
    @Query("SELECT * FROM song_templates")
    fun getAllSongTemplates(): Flow<List<SongTemplateEntity>>

    @Query("SELECT * FROM song_templates WHERE id = :id")
    suspend fun getSongTemplateById(id: String): SongTemplateEntity?

    @Insert
    suspend fun insertSongTemplate(songTemplate: SongTemplateEntity)

    @Update
    suspend fun updateSongTemplate(songTemplate: SongTemplateEntity)

    @Delete
    suspend fun deleteSongTemplate(songTemplate: SongTemplateEntity)
}
