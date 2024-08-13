package ru.betel.app.view_model.song

import android.os.CountDownTimer
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.betel.data.extensions.toDeleteFavoriteEntity
import ru.betel.data.extensions.toInsertFavoriteEntity
import ru.betel.domain.model.Song
import ru.betel.domain.model.ui.SongsCategory
import ru.betel.domain.useCase.favorite.DeleteFavoriteSongsUseCase
import ru.betel.domain.useCase.favorite.GetFavoriteSongsUseCase
import ru.betel.domain.useCase.favorite.InsertFavoriteSongsUseCase
import ru.betel.domain.useCase.share.ShareSongUseCase
import ru.betel.domain.useCase.song.GetAllSongsUseCase
import ru.betel.domain.useCase.song.category.GetFromSongbookSongsUseCase
import ru.betel.domain.useCase.song.category.GetGiftSongsUseCase
import ru.betel.domain.useCase.song.category.GetGlorifyingSongsUseCase
import ru.betel.domain.useCase.song.category.GetWorshipSongsUseCase
import ru.betel.domain.useCase.song.delete.DeleteSongFromFirebaseUseCase
import ru.betel.domain.useCase.song.delete.DeleteSongInFirebaseWithoutIdUseCase
import ru.betel.domain.useCase.song.set.SaveSongInFirebaseUseCase
import ru.betel.domain.useCase.sync.song.SyncSongFromFbToLocalStorageUseCase

class SongViewModel(
    private val getAllSongsUseCase: GetAllSongsUseCase,
    private val syncSongFromFbToLocalStorageUseCase: SyncSongFromFbToLocalStorageUseCase,
    getGlorifyingSongsUseCase: GetGlorifyingSongsUseCase,
    getWorshipSongsUseCase: GetWorshipSongsUseCase,
    getGiftSongsUseCase: GetGiftSongsUseCase,
    getFromSongbookSongsUseCase: GetFromSongbookSongsUseCase,
    private val shareSongUseCase: ShareSongUseCase,
    private val getFavoriteSongsUseCase: GetFavoriteSongsUseCase,
    private val insertFavoriteSongsUseCase: InsertFavoriteSongsUseCase,
    private val deleteFavoriteSongsUseCase: DeleteFavoriteSongsUseCase,
    private val saveSongInFirebaseUseCase: SaveSongInFirebaseUseCase,
    private val deleteSongFromFirebaseUseCase: DeleteSongFromFirebaseUseCase,
    private val deleteSongInfFirebaseWithoutIdUseCase: DeleteSongInFirebaseWithoutIdUseCase
) : ViewModel() {

    val searchAppBarText = mutableStateOf("")
    val selectedSongIndex = mutableStateOf<Int>(0)
    val selectedSongList = mutableStateOf<List<Song>>(mutableListOf())

    var allSongState = getAllSongsUseCase.execute()

    var glorifyingSongsState = getGlorifyingSongsUseCase.execute()

    var worshipSongsState = getWorshipSongsUseCase.execute()

    var giftSongsSongsState = getGiftSongsUseCase.execute()

    var fromSongbookState = getFromSongbookSongsUseCase.execute()

    private val _isLoadingContainer = mutableStateOf(false)
    val isLoadingContainer = _isLoadingContainer

    val selectedCategory = MutableStateFlow(SongsCategory.GLORIFYING)
    val isDropdownMenuVisible = mutableStateOf(false)

    val selectedSong = MutableStateFlow(
        Song(
            id = "Error",
            title = "Error",
            tonality = "Error",
            words = "Error",
            isGlorifyingSong = false,
            isWorshipSong = false,
            isGiftSong = false,
            isFromSongbookSong = false,
            temp = "Error"
        )
    )

    private val _timerValue = mutableLongStateOf(0L)

    private var countDownTimer: CountDownTimer? = null


    fun syncSongs() {
        viewModelScope.launch {
            delay(7000)
            syncSongFromFbToLocalStorageUseCase.execute()
        }
    }

    fun startTimer(seconds: Long) {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(seconds * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _timerValue.longValue = millisUntilFinished / 1000
            }

            override fun onFinish() {
                _timerValue.longValue = 0
                syncSongs()
            }
        }.start()
    }

    fun loadSong() {
        viewModelScope.launch {
            _isLoadingContainer.value = true
            val songs = getAllSongsUseCase.execute()
            allSongState = songs

            _isLoadingContainer.value = false
        }
    }

    fun onCategorySelected(category: SongsCategory) {
        println("selected category ${category.title}")
        selectedCategory.value = category
        isDropdownMenuVisible.value = false
    }

    fun saveSongToFirebase(song: Song): Boolean {
        song.id = "Song"
        return saveSongInFirebaseUseCase.execute(song)
    }

    fun shareSong(song: Song) {
        viewModelScope.launch {
            shareSongUseCase.execute(song)
        }
    }

    override fun onCleared() {
        super.onCleared()
        countDownTimer?.cancel()
    }

    val favoriteSongs = MutableLiveData<List<Song>>().apply {
        viewModelScope.launch {
            getFavoriteSongsUseCase.execute().collect {
                value = it
            }
        }
    }

    fun insertFavoriteSongs() {
        viewModelScope.launch(Dispatchers.IO) {
            insertFavoriteSongsUseCase.execute(selectedSong.value.toInsertFavoriteEntity())
        }
    }

    fun insertFavoriteSongs(song: Song) {
        viewModelScope.launch(Dispatchers.IO) {
            insertFavoriteSongsUseCase.execute(song.toInsertFavoriteEntity())
        }
    }

    fun deleteFavoriteSongs(item: Song) {
        viewModelScope.launch(Dispatchers.IO) {
            val deletingItem = favoriteSongs.value?.filter {
                it.title == item.title && it.words == item.words
            }
            deletingItem?.first()?.toDeleteFavoriteEntity()
                ?.let { deleteFavoriteSongsUseCase.execute(it) }
        }
    }

    fun deleteSongFromFirebase(song: Song) {
        viewModelScope.launch {
            deleteSongFromFirebaseUseCase.execute(song)
        }
    }

    fun deleteSongFromFirebaseWithoutId(song: Song, allSongs: List<Song>) {
        viewModelScope.launch {
            deleteSongInfFirebaseWithoutIdUseCase.execute(song, allSongs)
        }
    }
}