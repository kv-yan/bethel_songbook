package ru.betel.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `template_temp` " +
                "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "`create_date` TEXT NOT NULL, " +
                "`performer_name` TEXT NOT NULL, " +
                "`weekday` TEXT NOT NULL, " +
                "`isSingleMode` INTEGER NOT NULL, " +
                "`glorifyingSong` TEXT NOT NULL, " +
                "`worshipSong` TEXT NOT NULL, " +
                "`giftSong` TEXT NOT NULL, " +
                "`singleModeSongs` TEXT NOT NULL)")

        database.execSQL("INSERT INTO `template_temp` (" +
                "`id`, `create_date`, `performer_name`, `weekday`, `isSingleMode`, " +
                "`glorifyingSong`, `worshipSong`, `giftSong`, `singleModeSongs`) " +
                "SELECT `id`, `create_date`, `performer_name`, `weekday`, `isSingleMode`, " +
                "`glorifyingSong`, `worshipSong`, `giftSong`, `singleModeSongs` FROM `template`")

        database.execSQL("DROP TABLE IF EXISTS `template`")

        database.execSQL("ALTER TABLE `template_temp` RENAME TO `template`")
    }
}
