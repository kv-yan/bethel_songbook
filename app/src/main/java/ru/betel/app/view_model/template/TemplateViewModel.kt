package ru.betel.app.view_model.template

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.betel.data.extensions.toImmutableAddSongList
import ru.betel.data.extensions.toSongTemplate
import ru.betel.domain.model.Song
import ru.betel.domain.model.SongTemplate
import ru.betel.domain.model.ui.AddSong
import ru.betel.domain.model.ui.TemplateType
import ru.betel.domain.useCase.favorite.GetFavoriteSongsUseCase
import ru.betel.domain.useCase.song.GetAllSongsUseCase
import ru.betel.domain.useCase.song.category.GetGiftSongsUseCase
import ru.betel.domain.useCase.song.category.GetGlorifyingSongsUseCase
import ru.betel.domain.useCase.song.category.GetWorshipSongsUseCase
import ru.betel.domain.useCase.template.get.GetTemplatesFromFirebaseUseCase
import ru.betel.domain.useCase.template.get.GetTemplatesFromLocalUseCase
import ru.betel.domain.useCase.template.set.SaveTemplateToLocalUseCase
import java.text.SimpleDateFormat
import java.util.Calendar

class TemplateViewModel(
    private val getAllTemplatesUseCase: GetTemplatesFromFirebaseUseCase,
    private val getAllSongsUseCase: GetAllSongsUseCase,
    getGlorifyingSongsUseCase: GetGlorifyingSongsUseCase,
    getWorshipSongsUseCase: GetWorshipSongsUseCase,
    getGiftSongsUseCase: GetGiftSongsUseCase,
    private val getFavoriteSongsUseCase: GetFavoriteSongsUseCase,
    private val getTemplatesFromLocalUseCase: GetTemplatesFromLocalUseCase,
    private val saveTemplateToLocalUseCase: SaveTemplateToLocalUseCase,
) : ViewModel() {
    val localTemplateState = MutableLiveData<List<SongTemplate>>().apply {
        viewModelScope.launch {
            getTemplatesFromLocalUseCase.execute().collect {
                value = it.toSongTemplate()
            }
        }
    }

    var templateState = getAllTemplatesUseCase.execute()

    val templateSelectedType = mutableStateOf(TemplateType.ALL)

    val singleTemplate = MutableStateFlow(
        SongTemplate(
            id = "Error",
            createDate = "Error",
            performerName = "Error",
            weekday = "Error",
            favorite = false,
            glorifyingSong = emptyList(),
            worshipSong = emptyList(),
            giftSong = emptyList()
        )
    )

    private val _tempCreateDate =
        mutableStateOf(SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time))
    val tempCreateDate = _tempCreateDate

    private val _tempPerformerName = mutableStateOf("")
    val tempPerformerName = _tempPerformerName

    private val _tempWeekday = mutableStateOf("")
    val tempWeekday = _tempWeekday

    private val _tempGlorifyingSongs = MutableLiveData<SnapshotStateList<Song>>().apply {
        value = mutableStateListOf<Song>()
    }
    val tempGlorifyingSongs = _tempGlorifyingSongs

    private val _tempWorship = MutableLiveData<SnapshotStateList<Song>>().apply {
        value = mutableStateListOf<Song>()
    }
    val tempWorshipSongs = _tempWorship

    private val _tempGift = MutableLiveData<SnapshotStateList<Song>>().apply {
        value = mutableStateListOf<Song>()
    }
    val tempGiftSongs = _tempGift

    /** ADD_SONGS for creating new template **/

    private val _tempGlorifyingAllAddSongs = MutableLiveData<MutableList<AddSong>>().apply {
        viewModelScope.launch {
            getAllSongsUseCase.execute().collect {
                value = it.toImmutableAddSongList()
            }
        }
    }

    private val _tempFavoriteAllAddSongs = MutableLiveData<MutableList<AddSong>>().apply {
        viewModelScope.launch {
            getFavoriteSongsUseCase.execute().collect {
                value = it.toImmutableAddSongList()
            }
        }
    }

    private val _tempWorshipAllAddSongs = MutableLiveData<MutableList<AddSong>>().apply {
        viewModelScope.launch {
            getAllSongsUseCase.execute().collect {
                value = it.toImmutableAddSongList()
            }
        }
    }

    private val _tempGiftAllAddSongs = MutableLiveData<MutableList<AddSong>>().apply {
        viewModelScope.launch {
            getAllSongsUseCase.execute().collect {
                value = it.toImmutableAddSongList()
            }
        }
    }

    private val _tempGlorifyingAddSongs = MutableLiveData<MutableList<AddSong>>().apply {
        viewModelScope.launch {
            getGlorifyingSongsUseCase.execute().collect {
                value = it.toImmutableAddSongList()
            }
        }
    }

    private val _tempWorshipAddSongs = MutableLiveData<MutableList<AddSong>>().apply {
        viewModelScope.launch {
            getWorshipSongsUseCase.execute().collect {
                value = it.toImmutableAddSongList()
            }
        }

    }

    private val _tempGiftAddSongs = MutableLiveData<MutableList<AddSong>>().apply {
        viewModelScope.launch {
            getGiftSongsUseCase.execute().collect {
                value = it.toImmutableAddSongList()
            }
        }
    }

    val tempGlorifyingAllAddSongs = _tempGlorifyingAllAddSongs
    val tempFavoriteAllAddSongs = _tempFavoriteAllAddSongs
    val tempWorshipAllAddSongs = _tempWorshipAllAddSongs
    val tempGiftAllAddSongs = _tempGiftAllAddSongs
    val glorifyingAddSong = _tempGlorifyingAddSongs
    val worshipAddSong = _tempWorshipAddSongs
    val giftAddSong = _tempGiftAddSongs

    fun saveSongToFirebase(songTemplate: SongTemplate) {
        songTemplate.id = "SongTemplate"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveSongTemplateToLocalStorage() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentTemplate = tempGiftSongs.value?.toList()?.let { giftSongs ->
                tempWorshipSongs.value?.toList()?.let { worshipSongs ->
                    tempGlorifyingSongs.value?.toList()?.let { glorifyingSongs ->
                        SongTemplate(
                            id = "Error",
                            createDate = tempCreateDate.value,
                            performerName = tempPerformerName.value,
                            weekday = tempWeekday.value,
                            favorite = false,
                            glorifyingSong = glorifyingSongs,
                            worshipSong = worshipSongs,
                            giftSong = giftSongs
                        )
                    }
                }
            }

            currentTemplate?.let { saveTemplateToLocalUseCase.execute(it) }
        }
    }

    fun checkFields(): Result<Unit> {
        return if (tempPerformerName.value.isNotEmpty()) {
            if (tempWeekday.value.isNotEmpty()) {
                if (tempGlorifyingSongs.value?.isNotEmpty() == true) {
                    if (tempWorshipSongs.value?.isNotEmpty() == true) {
                        if (tempGiftSongs.value?.isNotEmpty() == true) {
                            Result.success(Unit)
                        } else Result.failure(IllegalAccessError())
                    } else Result.failure(IllegalAccessError())
                } else Result.failure(IllegalAccessError())
            } else Result.failure(IllegalAccessError())
        } else Result.failure(IllegalAccessError())
    }


    fun onTemplateTypeSelected(type: TemplateType) {
        templateSelectedType.value = type
    }


    fun loadTemplate() {
        templateState = getAllTemplatesUseCase.execute()
    }
}