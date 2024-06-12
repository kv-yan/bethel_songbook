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
import ru.betel.domain.useCase.song.set.SaveSongInFirebaseUseCase
import ru.betel.domain.useCase.favorite.DeleteFavoriteSongsUseCase
import ru.betel.domain.useCase.favorite.GetFavoriteSongsUseCase
import ru.betel.domain.useCase.favorite.InsertFavoriteSongsUseCase
import ru.betel.domain.useCase.share.ShareSongUseCase
import ru.betel.domain.useCase.song.GetAllSongsUseCase
import ru.betel.domain.useCase.song.category.GetFromSongbookSongsUseCase
import ru.betel.domain.useCase.song.category.GetGiftSongsUseCase
import ru.betel.domain.useCase.song.category.GetGlorifyingSongsUseCase
import ru.betel.domain.useCase.song.category.GetWorshipSongsUseCase
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
    private val saveSongInFirebaseUseCase: SaveSongInFirebaseUseCase
) : ViewModel() {

    val searchAppBarText = mutableStateOf("")

    var allSongState = getAllSongsUseCase.execute()

    var glorifyingSongsState = getGlorifyingSongsUseCase.execute()

    var worshipSongsState = getWorshipSongsUseCase.execute()

    var giftSongsSongsState = getGiftSongsUseCase.execute()

    var fromSongbookState = getFromSongbookSongsUseCase.execute()

    private val _isLoadingContainer = mutableStateOf(false)
    val isLoadingContainer = _isLoadingContainer

    val selectedCategory = MutableStateFlow(SongsCategory.GLORIFYING)
    val isDropdownMenuVisible = mutableStateOf(false)

    private val _tempIsGlorifyingSongs = mutableStateOf(false)
    val tempIsGlorifyingSongs = _tempIsGlorifyingSongs

    private val _tempIsWorship = mutableStateOf(false)
    val tempIsWorship = _tempIsWorship

    private val _tempIsGift = mutableStateOf(false)
    val tempIsGift = _tempIsGift

    private val _tempIsFromSongbook = mutableStateOf(false)
    val tempIsFromSongbook = _tempIsFromSongbook

    private val _tempWords = mutableStateOf("")
    val tempWords = _tempWords

    private val _tempTitle = mutableStateOf("")
    val tempTitle = _tempTitle

    private val _tempTonality = mutableStateOf("")
    val tempTonality = _tempTonality

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
        )
    )

    private val _timerValue = mutableLongStateOf(0L)

    private var countDownTimer: CountDownTimer? = null


    fun syncSongs() {
        viewModelScope.launch {
            delay(30000 /*after half min */)
            println("started sync songs")
            syncSongFromFbToLocalStorageUseCase.execute()
            println("ended sync logic")
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

    fun saveSongToFirebase(song: Song) {
        song.id = "Song"
        saveSongInFirebaseUseCase.execute(song)
    }

    fun shareSong(song: Song) {
        viewModelScope.launch {
            shareSongUseCase.execute(song)
        }
    }

    fun cleanAllFields() {
        _tempIsGift.value = false
        _tempIsGlorifyingSongs.value = false
        _tempIsWorship.value = false
        _tempIsFromSongbook.value = false

        _tempTitle.value = ""
        _tempWords.value = ""
        _tempTonality.value = ""

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

    fun deleteFavoriteSongs(item: Song) {
        viewModelScope.launch(Dispatchers.IO) {
            val deletingItem = favoriteSongs.value?.filter {
                it.title == item.title && it.words == item.words
            }
            deletingItem?.first()?.toDeleteFavoriteEntity()
                ?.let { deleteFavoriteSongsUseCase.execute(it) }
        }
    }
}