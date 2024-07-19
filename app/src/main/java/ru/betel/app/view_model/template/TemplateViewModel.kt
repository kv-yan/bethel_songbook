package ru.betel.app.view_model.template

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.betel.data.extensions.toImmutableAddSongList
import ru.betel.data.extensions.toSongTemplate
import ru.betel.domain.model.Song
import ru.betel.domain.model.SongTemplate
import ru.betel.domain.model.ui.AddSong
import ru.betel.domain.model.ui.NewTemplateFieldState
import ru.betel.domain.model.ui.TemplateType
import ru.betel.domain.useCase.favorite.GetFavoriteSongsUseCase
import ru.betel.domain.useCase.notification.SendNotificationToAllUsersUseCase
import ru.betel.domain.useCase.share.ShareTemplateUseCase
import ru.betel.domain.useCase.song.GetAllSongsUseCase
import ru.betel.domain.useCase.song.category.GetGiftSongsUseCase
import ru.betel.domain.useCase.song.category.GetGlorifyingSongsUseCase
import ru.betel.domain.useCase.song.category.GetWorshipSongsUseCase
import ru.betel.domain.useCase.song.delete.DeleteTemplateFromFirebaseUseCase
import ru.betel.domain.useCase.song.delete.DeleteTemplateFromLocalUseCase
import ru.betel.domain.useCase.template.get.GetTemplatesFromFirebaseUseCase
import ru.betel.domain.useCase.template.get.GetTemplatesFromLocalUseCase
import ru.betel.domain.useCase.template.set.SaveTemplateInFirebaseUseCase
import ru.betel.domain.useCase.template.set.SaveTemplateToLocalUseCase
import java.text.Normalizer
import java.util.Locale

class TemplateViewModel(
    private val getAllTemplatesUseCase: GetTemplatesFromFirebaseUseCase,
    private val getAllSongsUseCase: GetAllSongsUseCase,
    getGlorifyingSongsUseCase: GetGlorifyingSongsUseCase,
    getWorshipSongsUseCase: GetWorshipSongsUseCase,
    getGiftSongsUseCase: GetGiftSongsUseCase,
    private val getFavoriteSongsUseCase: GetFavoriteSongsUseCase,
    private val getTemplatesFromLocalUseCase: GetTemplatesFromLocalUseCase,
    private val saveTemplateToLocalUseCase: SaveTemplateToLocalUseCase,
    private val saveTemplateInFirebaseUseCase: SaveTemplateInFirebaseUseCase,
    private val deleteTemplateFromFirebaseUseCase: DeleteTemplateFromFirebaseUseCase,
    private val deleteTemplateFromLocalUseCase: DeleteTemplateFromLocalUseCase,
    private val shareTemplateUseCase: ShareTemplateUseCase,
    private val sendNotificationToAllUsersUseCase: SendNotificationToAllUsersUseCase
) : ViewModel() {
    val localTemplateState = MutableLiveData<List<SongTemplate>>().apply {
        viewModelScope.launch {
            getTemplatesFromLocalUseCase.execute().collect {
                value = it.toSongTemplate()
            }
        }
    }

    private var templateState = getAllTemplatesUseCase.execute()

    val templateUiState = mutableStateOf<List<SongTemplate>>(emptyList())

    val templateSelectedType = mutableStateOf(TemplateType.ALL)
    val createdNewTemplate = mutableStateOf<SongTemplate?>(null)

    val singleTemplate = MutableStateFlow(
        SongTemplate(
            id = "Error",
            createDate = "Error",
            performerName = "Error",
            weekday = "Error",
            isSingleMode = false,
            glorifyingSong = emptyList(),
            worshipSong = emptyList(),
            giftSong = emptyList(),
            singleModeSongs = emptyList()
        )
    )

    private val _tempPerformerName = mutableStateOf("")
    val tempPerformerName = _tempPerformerName

    private val _tempWeekday = mutableStateOf("Շաբաթվա օր")
    val tempWeekday = _tempWeekday

    private val _planningDay = mutableStateOf("Ամսաթիվ")
    val planningDay = _planningDay

    private val _tempGlorifying = MutableLiveData<SnapshotStateList<Song>>().apply {
        value = mutableStateListOf<Song>()
    }
    val tempGlorifyingSongs = _tempGlorifying

    private val _tempWorship = MutableLiveData<SnapshotStateList<Song>>().apply {
        value = mutableStateListOf<Song>()
    }
    val tempWorshipSongs = _tempWorship

    private val _tempGift = MutableLiveData<SnapshotStateList<Song>>().apply {
        value = mutableStateListOf<Song>()
    }
    val tempGiftSongs = _tempGift

    private val _tempSingleMode = MutableLiveData<SnapshotStateList<Song>>().apply {
        value = mutableStateListOf<Song>()
    }
    val tempSingleModeSongs = _tempSingleMode

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
            getGiftSongsUseCase.execute().collect {
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

    private val _tempSingleModeAddSongs = MutableLiveData<MutableList<AddSong>>().apply {
        viewModelScope.launch {
            getAllSongsUseCase.execute().collect {
                value = mutableListOf<Song>().toImmutableAddSongList()
            }
        }
    }

    private val _isSingleMode = mutableStateOf(false)

    val tempGlorifyingAllAddSongs = _tempGlorifyingAllAddSongs
    val tempFavoriteAllAddSongs = _tempFavoriteAllAddSongs
    val tempWorshipAllAddSongs = _tempWorshipAllAddSongs
    val tempGiftAllAddSongs = _tempGiftAllAddSongs
    val tempSingleModeAddSongs = _tempSingleModeAddSongs

    val glorifyingAddSong = _tempGlorifyingAddSongs
    val worshipAddSong = _tempWorshipAddSongs
    val giftAddSong = _tempGiftAddSongs
    val singleModeAddSong = _tempSingleModeAddSongs

    val isSingleMode = _isSingleMode

    fun saveTemplateToFirebase(songTemplate: SongTemplate) {
        songTemplate.id = "SongTemplate"
        saveTemplateInFirebaseUseCase.execute(songTemplate)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun saveTemplateToLocalStorage(template: SongTemplate) {
        viewModelScope.launch(Dispatchers.IO) {
            saveTemplateToLocalUseCase.execute(template)
        }
    }

    fun checkFields(templateFieldState: MutableState<NewTemplateFieldState>): Result<Unit> {
        return if (tempPerformerName.value.isNotEmpty()) {
            if (tempWeekday.value.isNotEmpty() && tempWeekday.value != "Շաբաթվա օր") {
                if (planningDay.value.isNotEmpty() && planningDay.value != "Ամսաթիվ") {
                    if (isSingleMode.value) {
                        if (tempSingleModeSongs.value?.isNotEmpty() == true) {
                            templateFieldState.value = NewTemplateFieldState.DONE
                            Result.success(Unit)
                        } else {
                            templateFieldState.value = NewTemplateFieldState.INVALID_SINGLE_MODE
                            Result.failure(IllegalAccessError())
                        }
                    } else {
                        if (tempGlorifyingSongs.value?.isNotEmpty() == true) {
                            if (tempWorshipSongs.value?.isNotEmpty() == true) {
                                if (tempGiftSongs.value?.isNotEmpty() == true) {
                                    templateFieldState.value = NewTemplateFieldState.DONE
                                    Result.success(Unit)
                                } else {
                                    templateFieldState.value = NewTemplateFieldState.INVALID_GIFT
                                    Result.failure(IllegalAccessError())
                                }
                            } else {
                                templateFieldState.value = NewTemplateFieldState.INVALID_WORSHIP
                                Result.failure(IllegalAccessError())
                            }
                        } else {
                            templateFieldState.value = NewTemplateFieldState.INVALID_GLORIFYING
                            Result.failure(IllegalAccessError())
                        }

                    }
                } else {
                    templateFieldState.value = NewTemplateFieldState.INVALID_DAY
                    Result.failure(IllegalAccessError())
                }
            } else {
                templateFieldState.value = NewTemplateFieldState.INVALID_WEEKDAY
                Result.failure(IllegalAccessError())
            }
        } else {
            templateFieldState.value = NewTemplateFieldState.INVALID_NAME
            Result.failure(IllegalAccessError())
        }

    }

    fun initCategorizedSongs() {
//        tempGlorifyingSongs.value = mutableStateListOf()
//        tempWorshipSongs.value = mutableStateListOf()
//        tempGiftSongs.value = mutableStateListOf()
        tempSingleModeSongs.value = mutableStateListOf()
    }

    fun initSingleMode() {
        tempGlorifyingSongs.value = mutableStateListOf()
        tempWorshipSongs.value = mutableStateListOf()
        tempGiftSongs.value = mutableStateListOf()
//        tempSingleModeSongs.value = mutableStateListOf()
    }

    fun checkFields(
        templateFieldState: MutableState<NewTemplateFieldState>,
        tempPerformerName: MutableState<String>,
        tempWeekday: MutableState<String>,
        planningDay: MutableState<String>,
        glorifyingSongsState: SnapshotStateList<Song>,
        worshipSongsState: SnapshotStateList<Song>,
        giftSongsState: SnapshotStateList<Song>,
    ): Result<Unit> {
        return if (tempPerformerName.value.isNotEmpty()) {
            if (tempWeekday.value.isNotEmpty() && tempWeekday.value != "Շաբաթվա օր") {
                if (planningDay.value.isNotEmpty() && planningDay.value != "Ամսաթիվ") {
                    if (glorifyingSongsState.isNotEmpty()) {
                        if (worshipSongsState.isNotEmpty()) {
                            if (giftSongsState.isNotEmpty()) {
                                templateFieldState.value = NewTemplateFieldState.DONE
                                Result.success(Unit)
                            } else {
                                templateFieldState.value = NewTemplateFieldState.INVALID_GIFT
                                Result.failure(IllegalAccessError())
                            }
                        } else {
                            templateFieldState.value = NewTemplateFieldState.INVALID_WORSHIP
                            Result.failure(IllegalAccessError())
                        }
                    } else {
                        templateFieldState.value = NewTemplateFieldState.INVALID_GLORIFYING
                        Result.failure(IllegalAccessError())
                    }
                } else {
                    templateFieldState.value = NewTemplateFieldState.INVALID_DAY
                    Result.failure(IllegalAccessError())
                }
            } else {
                templateFieldState.value = NewTemplateFieldState.INVALID_WEEKDAY
                Result.failure(IllegalAccessError())
            }
        } else {
            templateFieldState.value = NewTemplateFieldState.INVALID_NAME
            Result.failure(IllegalAccessError())
        }

    }

    fun onTemplateTypeSelected(type: TemplateType) {
        templateSelectedType.value = type
    }

    fun loadTemplate() {
        templateState = getAllTemplatesUseCase.execute()
        viewModelScope.launch {
            getAllTemplatesUseCase.execute().collect { templates ->
                templateUiState.value = templates
            }

            getTemplatesFromLocalUseCase.execute().collect {
                localTemplateState.value = it.toSongTemplate()
            }
        }
    }

    fun deleteTemplateFromFirebase(it: SongTemplate) {
        viewModelScope.launch { deleteTemplateFromFirebaseUseCase.execute(it) }
    }

    fun deleteTemplateFromLocal(it: SongTemplate) {
        viewModelScope.launch { deleteTemplateFromLocalUseCase.execute(it) }
    }

    fun shareTemplate(it: SongTemplate) {
        viewModelScope.launch { shareTemplateUseCase.execute(it) }
    }

    fun cleanFields() {
        tempGlorifyingSongs.value?.clear()
        tempWorshipSongs.value?.clear()
        tempGiftSongs.value?.clear()

        tempPerformerName.value = ""
        tempWeekday.value = ""
        planningDay.value = ""
    }

    val searchQuery = mutableStateOf("")

    fun searchTemplates(query: String): List<SongTemplate> {
        if (query.isBlank()) return templateUiState.value

        val normalizedQuery = normalizeText(query)
        return templateUiState.value.filter { template ->
            template.glorifyingSong.any { normalizeText(it.title).contains(normalizedQuery) } || template.worshipSong.any {
                normalizeText(
                    it.title
                ).contains(normalizedQuery)
            } || template.giftSong.any { normalizeText(it.title).contains(normalizedQuery) }
        }
    }

    private fun normalizeText(text: String): String {
        return Normalizer.normalize(text, Normalizer.Form.NFD).replace("[\\p{M}]".toRegex(), "")
            .replace("[&\\/`՝#,+()$~%.'\":*?<>{}br0-9\\s]+".toRegex(), "")
            .lowercase(Locale.getDefault())
    }

    fun sendNotification(item: SongTemplate) {
        viewModelScope.launch {
            sendNotificationToAllUsersUseCase.execute(item)
        }
    }
}

