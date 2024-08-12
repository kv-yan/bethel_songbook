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
    version = 2
)
@TypeConverters(SongListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun songTemplateDao(): TemplateDao
    abstract fun songDao(): SongDao
    abstract fun favoriteSongsDao(): FavoriteSongsDao
}

val MIGRATION_TEMPLATE_1_2 = object : Migration(1, 2) {
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

val MIGRATION_SONG_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Step 1: Create a new temporary table with the updated schema
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `song_temp` (
                `id` TEXT NOT NULL PRIMARY KEY, 
                `title` TEXT NOT NULL, 
                `tonality` TEXT NOT NULL, 
                `words` TEXT NOT NULL, 
                `temp` TEXT NOT NULL, 
                `is_glorifying_song` INTEGER NOT NULL, 
                `is_worship_song` INTEGER NOT NULL, 
                `is_gift_song` INTEGER NOT NULL, 
                `is_from_songbook_song` INTEGER NOT NULL
            )
            """.trimIndent()
        )

        // Step 2: Copy data from the old table to the new table
        database.execSQL(
            """
            INSERT INTO `song_temp` (
                `id`, `title`, `tonality`, `words`, `temp`, 
                `is_glorifying_song`, `is_worship_song`, 
                `is_gift_song`, `is_from_songbook_song`
            )
            SELECT 
                `id`, `title`, `tonality`, `words`, `temp`, 
                `is_glorifying_song`, `is_worship_song`, 
                `is_gift_song`, `is_from_songbook_song`
            FROM `song`
            """.trimIndent()
        )

        // Step 3: Drop the old table
        database.execSQL("DROP TABLE IF EXISTS `song`")

        // Step 4: Rename the new table to the original table name
        database.execSQL("ALTER TABLE `song_temp` RENAME TO `song`")
    }
}
