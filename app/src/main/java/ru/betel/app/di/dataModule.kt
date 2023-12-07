package ru.betel.app.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.koin.dsl.module
import ru.betel.data.reopsitory.auth.FirebaseAuthRepoImpl
import ru.betel.data.reopsitory.favorite.FavoriteSongsRepoImpl
import ru.betel.data.reopsitory.network.NetworkUtilsImpl
import ru.betel.data.reopsitory.song.get.all.GetAllSongsImpl
import ru.betel.data.reopsitory.song.get.category.GetFromSongbookSongsImpl
import ru.betel.data.reopsitory.song.get.category.GetGiftSongsImpl
import ru.betel.data.reopsitory.song.get.category.GetGlorifyingSongsImpl
import ru.betel.data.reopsitory.song.get.category.GetWorshipSongsImpl
import ru.betel.data.reopsitory.song.get.firebase.GetSongFromFirebaseImpl
import ru.betel.data.reopsitory.song.get.local.GetSongsFromLocalImpl
import ru.betel.data.reopsitory.sync.song.SyncSongsFromFBToLocalStorageImpl
import ru.betel.data.reopsitory.template.get.GetTemplatesFromFirebaseImpl
import ru.betel.data.reopsitory.template.get.GetTemplatesFromLocalImpl
import ru.betel.data.reopsitory.template.set.SaveTemplateToLocalImpl
import ru.betel.data.share.ShareRepoImpl
import ru.betel.domain.dao.FavoriteSongsDao
import ru.betel.domain.repository.auth.FirebaseAuthRepo
import ru.betel.domain.repository.network.NetworkUtils
import ru.betel.domain.repository.share.ShareRepo
import ru.betel.domain.repository.song.get.all.GetAllSongs
import ru.betel.domain.repository.song.get.category.GetFromSongbookSongs
import ru.betel.domain.repository.song.get.category.GetGiftSongs
import ru.betel.domain.repository.song.get.category.GetGlorifyingSongs
import ru.betel.domain.repository.song.get.category.GetWorshipSongs
import ru.betel.domain.repository.song.get.firebase.GetSongsFromFirebase
import ru.betel.domain.repository.song.get.local.GetSongsFromLocal
import ru.betel.domain.dao.SongDao
import ru.betel.domain.repository.song.sync.SyncSongsFromFBToLocalStorage
import ru.betel.domain.repository.template.get.GetTemplatesFromFirebase
import ru.betel.domain.repository.template.get.GetTemplatesFromLocal
import ru.betel.domain.repository.template.set.local.SaveTemplateToLocal
import ru.betel.domain.dao.TemplateDao
import ru.betel.domain.repository.favorite.FavoriteSongsRepo
import ru.betel.domain.useCase.song.local.GetSongsFromLocalUseCase


val dataModule = module {

    single<GetTemplatesFromFirebase> {
        GetTemplatesFromFirebaseImpl(database = get())
    }

    single<GetSongsFromFirebase> {
        GetSongFromFirebaseImpl(database = get<FirebaseDatabase>())
    }

    single<GetSongsFromLocal> {
        GetSongsFromLocalImpl(get<SongDao>())
    }

    single<GetAllSongs> {
        println("------------------------ Times on get all songs ------------------------")
        GetAllSongsImpl(
            getSongFromFirebase = get<GetSongsFromFirebase>(),
            getNetworkUtils = get<NetworkUtils>(),
            getSongsFromLocal = get<GetSongsFromLocalUseCase>()
        )
    }

    single<SyncSongsFromFBToLocalStorage> {
        SyncSongsFromFBToLocalStorageImpl(
            getSongsFromFirebase = get<GetSongsFromFirebase>(),
            songDao = get<SongDao>()
        )
    }

    single<FirebaseDatabase> {
        FirebaseDatabase.getInstance()
    }

    single<ShareRepo> {
        ShareRepoImpl(context = get<Context>())
    }

    single<FirebaseAuth> {
        FirebaseAuth.getInstance()
    }

    single<FirebaseAuthRepo> {
        FirebaseAuthRepoImpl(firebaseAuth = get<FirebaseAuth>())
    }

    single<NetworkUtils> {
        NetworkUtilsImpl(get<Context>())
    }

    single<GetGlorifyingSongs> {
        GetGlorifyingSongsImpl(getAllSongs = get<GetAllSongs>())
    }

    single<GetWorshipSongs> {
        GetWorshipSongsImpl(getAllSongs = get<GetAllSongs>())
    }

    single<GetGiftSongs> {
        GetGiftSongsImpl(getAllSongs = get<GetAllSongs>())
    }

    single<GetFromSongbookSongs> {
        println("worked di :: GetFromSongbookSongsImpl")
        GetFromSongbookSongsImpl(getAllSongs = get<GetAllSongs>())
    }

    single<GetTemplatesFromLocal> {
        GetTemplatesFromLocalImpl(templateDao = get<TemplateDao>())
    }

    single<SaveTemplateToLocal> {
        SaveTemplateToLocalImpl(templateDao = get<TemplateDao>())
    }

    single<FavoriteSongsRepo> {
        FavoriteSongsRepoImpl(favSongDao = get<FavoriteSongsDao>())
    }

}
