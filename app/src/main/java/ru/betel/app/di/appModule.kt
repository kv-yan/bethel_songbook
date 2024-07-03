package ru.betel.app.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.betel.app.view_model.edit.EditViewModel
import ru.betel.app.view_model.settings.SettingViewModel
import ru.betel.app.view_model.song.SongViewModel
import ru.betel.app.view_model.template.TemplateViewModel
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
import ru.betel.domain.useCase.song.delete.DeleteSongFromFirebaseUseCase
import ru.betel.domain.useCase.song.set.SaveSongInFirebaseUseCase
import ru.betel.domain.useCase.song.update.UpdateSongInFirebaseUseCase
import ru.betel.domain.useCase.sync.song.SyncSongFromFbToLocalStorageUseCase
import ru.betel.domain.useCase.template.get.GetTemplatesFromFirebaseUseCase
import ru.betel.domain.useCase.template.get.GetTemplatesFromLocalUseCase
import ru.betel.domain.useCase.template.set.SaveTemplateInFirebaseUseCase
import ru.betel.domain.useCase.template.set.SaveTemplateToLocalUseCase

val appModule = module {
    single<SharedPreferences> {
        androidContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
    }

    viewModel {
        SongViewModel(
            getAllSongsUseCase = get<GetAllSongsUseCase>(),
            syncSongFromFbToLocalStorageUseCase = get<SyncSongFromFbToLocalStorageUseCase>(),
            getGlorifyingSongsUseCase = get<GetGlorifyingSongsUseCase>(),
            getWorshipSongsUseCase = get<GetWorshipSongsUseCase>(),
            getGiftSongsUseCase = get<GetGiftSongsUseCase>(),
            getFromSongbookSongsUseCase = get<GetFromSongbookSongsUseCase>(),
            shareSongUseCase = get<ShareSongUseCase>(),
            getFavoriteSongsUseCase = get<GetFavoriteSongsUseCase>(),
            insertFavoriteSongsUseCase = get<InsertFavoriteSongsUseCase>(),
            deleteFavoriteSongsUseCase = get<DeleteFavoriteSongsUseCase>(),
            saveSongInFirebaseUseCase = get<SaveSongInFirebaseUseCase>(),
            deleteSongFromFirebaseUseCase = get<DeleteSongFromFirebaseUseCase>()
        )
    }

    viewModel {
        TemplateViewModel(
            getAllTemplatesUseCase = get<GetTemplatesFromFirebaseUseCase>(),
            getAllSongsUseCase = get<GetAllSongsUseCase>(),
            getGlorifyingSongsUseCase = get<GetGlorifyingSongsUseCase>(),
            getWorshipSongsUseCase = get<GetWorshipSongsUseCase>(),
            getGiftSongsUseCase = get<GetGiftSongsUseCase>(),
            getTemplatesFromLocalUseCase = get<GetTemplatesFromLocalUseCase>(),
            saveTemplateToLocalUseCase = get<SaveTemplateToLocalUseCase>(),
            getFavoriteSongsUseCase = get<GetFavoriteSongsUseCase>(),
            saveTemplateInFirebaseUseCase = get<SaveTemplateInFirebaseUseCase>()
        )
    }

    viewModel {
        SettingViewModel(
            sharedPerf = get<SharedPreferences>(),
            logInUseCase = get<LogInUseCase>(),
            logOutUseCase = get<LogOutUseCase>(),
            checkUserLoginStatusUseCase = get<CheckUserLoginStatusUseCase>(),
            getNetworkStateUseCase = get<GetNetworkStateUseCase>(),
        )
    }

    viewModel {
        EditViewModel(
            updateSongInFirebaseUseCase = get<UpdateSongInFirebaseUseCase>()
        )
    }
}

