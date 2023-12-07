package ru.betel.app.di

import org.koin.dsl.module
import ru.betel.data.database.AppDatabase
import ru.betel.domain.dao.FavoriteSongsDao
import ru.betel.domain.dao.SongDao
import ru.betel.domain.dao.TemplateDao

val daoModules = module {
    single<TemplateDao> { get<AppDatabase>().songTemplateDao() }

    single<SongDao> { get<AppDatabase>().songDao() }

    single<FavoriteSongsDao> { get<AppDatabase>().favoriteSongsDao() }
}