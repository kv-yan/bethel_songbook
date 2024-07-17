package ru.betel.app.di

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import ru.betel.data.database.AppDatabase
import ru.betel.data.database.MIGRATION_1_2

val databaseModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            androidApplication(), AppDatabase::class.java, "template"
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }
}