package ru.betel.domain.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.betel.domain.model.entity.SongTemplateEntity

@Dao
interface TemplateDao {
    @Query("SELECT * FROM template")
    fun getAllSongTemplates(): Flow<List<SongTemplateEntity>>

    @Query("SELECT * FROM template WHERE id = :id")
    suspend fun getSongTemplateById(id: String): SongTemplateEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSongTemplate(songTemplate: SongTemplateEntity)

    @Update
    suspend fun updateSongTemplate(songTemplate: SongTemplateEntity)

    @Delete
    suspend fun deleteSongTemplate(songTemplate: SongTemplateEntity)
}
