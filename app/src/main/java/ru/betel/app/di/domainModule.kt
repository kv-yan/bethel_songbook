package ru.betel.app.di

import org.koin.dsl.module
import ru.betel.domain.repository.auth.FirebaseAuthRepo
import ru.betel.domain.repository.favorite.FavoriteSongsRepo
import ru.betel.domain.repository.network.NetworkUtils
import ru.betel.domain.repository.share.ShareRepo
import ru.betel.domain.repository.song.get.all.GetAllSongs
import ru.betel.domain.repository.song.get.category.GetFromSongbookSongs
import ru.betel.domain.repository.song.get.category.GetGiftSongs
import ru.betel.domain.repository.song.get.category.GetGlorifyingSongs
import ru.betel.domain.repository.song.get.category.GetWorshipSongs
import ru.betel.domain.repository.song.get.local.GetSongsFromLocal
import ru.betel.domain.repository.song.sync.SyncSongsFromFBToLocalStorage
import ru.betel.domain.repository.template.get.GetTemplatesFromFirebase
import ru.betel.domain.repository.template.get.GetTemplatesFromLocal
import ru.betel.domain.repository.template.set.local.SaveTemplateToLocal
import ru.betel.domain.useCase.auth.CheckUserLoginStatusUseCase
import ru.betel.domain.useCase.auth.LogInUseCase
import ru.betel.domain.useCase.auth.LogOutUseCase
import ru.betel.domain.useCase.favorite.DeleteFavoriteSongsUseCase
import ru.betel.domain.useCase.favorite.GetFavoriteSongsUseCase
import ru.betel.domain.useCase.favorite.InsertFavoriteSongsUseCase
import ru.betel.domain.useCase.network.GetNetworkStateUseCase
import ru.betel.domain.useCase.share.ShareSongUseCase
import ru.betel.domain.useCase.song.GetAllSongsUseCase
import ru.betel.domain.useCase.song.category.GetFromSongbookSongsUseCase
import ru.betel.domain.useCase.song.category.GetGiftSongsUseCase
import ru.betel.domain.useCase.song.category.GetGlorifyingSongsUseCase
import ru.betel.domain.useCase.song.category.GetWorshipSongsUseCase
import ru.betel.domain.useCase.song.local.GetSongsFromLocalUseCase
import ru.betel.domain.useCase.song.set.SaveSongInFirebaseUseCase
import ru.betel.domain.useCase.song.update.UpdateSongInFirebaseUseCase
import ru.betel.domain.useCase.sync.song.SyncSongFromFbToLocalStorageUseCase
import ru.betel.domain.useCase.template.get.GetTemplatesFromFirebaseUseCase
import ru.betel.domain.useCase.template.get.GetTemplatesFromLocalUseCase
import ru.betel.domain.useCase.template.set.SaveTemplateToLocalUseCase

val domainModule = module {
    single<GetAllSongsUseCase> {
        GetAllSongsUseCase(
            getAllSongs = get<GetAllSongs>()
        )
    }

    single<GetTemplatesFromFirebaseUseCase> {
        GetTemplatesFromFirebaseUseCase(getSongFromFirebase = get<GetTemplatesFromFirebase>())
    }

    single<GetNetworkStateUseCase> {
        GetNetworkStateUseCase(networkUtils = get<NetworkUtils>())
    }

    single<GetSongsFromLocalUseCase> {
        GetSongsFromLocalUseCase(getSongsFromLocal = get<GetSongsFromLocal>())
    }

    single<SyncSongFromFbToLocalStorageUseCase> {
        SyncSongFromFbToLocalStorageUseCase(syncSongsFromFBToLocalStorage = get<SyncSongsFromFBToLocalStorage>())
    }

    single<GetGlorifyingSongsUseCase> {
        GetGlorifyingSongsUseCase(getGlorifyingSong = get<GetGlorifyingSongs>())
    }

    single<GetWorshipSongsUseCase> {
        GetWorshipSongsUseCase(getWorshipSongs = get<GetWorshipSongs>())
    }

    single<GetGiftSongsUseCase> {
        GetGiftSongsUseCase(getGiftSongs = get<GetGiftSongs>())
    }

    single<SaveSongInFirebaseUseCase> {
        SaveSongInFirebaseUseCase()
    }

    single<UpdateSongInFirebaseUseCase> {
        UpdateSongInFirebaseUseCase()
    }

    single<GetFromSongbookSongsUseCase> {
        GetFromSongbookSongsUseCase(getFromSongbookSongs = get<GetFromSongbookSongs>())
    }

    single<LogInUseCase> {
        LogInUseCase(authRepo = get<FirebaseAuthRepo>())
    }

    single<LogOutUseCase> {
        LogOutUseCase(authRepo = get<FirebaseAuthRepo>())
    }

    single<CheckUserLoginStatusUseCase> {
        CheckUserLoginStatusUseCase(authRepo = get<FirebaseAuthRepo>())
    }

    single<ShareSongUseCase> {
        ShareSongUseCase(shareRepo = get<ShareRepo>())
    }

    single<GetTemplatesFromLocalUseCase> {
        GetTemplatesFromLocalUseCase(getTemplatesFromLocal = get<GetTemplatesFromLocal>())
    }

    single<SaveTemplateToLocalUseCase> {
        SaveTemplateToLocalUseCase(saveTemplateToLocal = get<SaveTemplateToLocal>())
    }

    single<GetFavoriteSongsUseCase> {
        GetFavoriteSongsUseCase(favoriteSongsRepo = get<FavoriteSongsRepo>())
    }

    single<InsertFavoriteSongsUseCase> {
        InsertFavoriteSongsUseCase(favoriteSongsRepo = get<FavoriteSongsRepo>())
    }

    single<DeleteFavoriteSongsUseCase> {
        DeleteFavoriteSongsUseCase(favoriteSongsRepo = get<FavoriteSongsRepo>())
    }
}