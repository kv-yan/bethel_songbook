package ru.betel.data.reopsitory.template.set

import android.util.Log
import ru.betel.domain.converters.toEntity
import ru.betel.domain.model.SongTemplate
import ru.betel.domain.repository.template.set.local.SaveTemplateToLocal
import ru.betel.domain.dao.TemplateDao

class SaveTemplateToLocalImpl(private val templateDao: TemplateDao) : SaveTemplateToLocal {
    override suspend fun saveTemplate(songTemplate: SongTemplate): Result<Unit> {
        return try {
            templateDao.insertSongTemplate(songTemplate.toEntity())
            Log.e("DAO", "saveTemplate: completed" )
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("DAO", "saveTemplate: ${e.message}" )
            Result.failure(e)
        }
    }
}