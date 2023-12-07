package ru.betel.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.betel.domain.converters.SongListConverter
import ru.betel.domain.model.entity.FavoriteSongsEntity
import ru.betel.domain.model.entity.SongEntity
import ru.betel.domain.model.entity.SongTemplateEntity
import ru.betel.domain.dao.FavoriteSongsDao
import ru.betel.domain.dao.SongDao
import ru.betel.domain.dao.TemplateDao

@Database(
    entities = [SongTemplateEntity::class, SongEntity::class, FavoriteSongsEntity::class],
    version = 1
)
@TypeConverters(SongListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun songTemplateDao(): TemplateDao

    abstract fun songDao(): SongDao

    abstract fun favoriteSongsDao(): FavoriteSongsDao
}


