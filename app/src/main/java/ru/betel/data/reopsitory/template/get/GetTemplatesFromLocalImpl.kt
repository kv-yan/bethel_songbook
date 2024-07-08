package ru.betel.data.reopsitory.template.get

import kotlinx.coroutines.flow.Flow
import ru.betel.domain.model.entity.SongTemplateEntity
import ru.betel.domain.repository.template.get.GetTemplatesFromLocal
import ru.betel.domain.dao.TemplateDao

class GetTemplatesFromLocalImpl(private val templateDao: TemplateDao):GetTemplatesFromLocal {
    override fun getTemplates(): Flow<List<SongTemplateEntity>> = templateDao.getAllSongTemplates()
}