package ru.betel.app.view_model.edit

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.betel.app.ui.widgets.pop_up.TemplateSaveMode
import ru.betel.domain.model.Song
import ru.betel.domain.model.SongTemplate
import ru.betel.domain.useCase.song.update.UpdateSongInFirebaseUseCase
import ru.betel.domain.useCase.template.update.UpdateTemplateInFirebaseUseCase
import ru.betel.domain.useCase.template.update.UpdateTemplateInLocalUseCase

class EditViewModel(
    private val updateSongInFirebaseUseCase: UpdateSongInFirebaseUseCase,
    private val updateTemplateInFirebaseUseCase: UpdateTemplateInFirebaseUseCase,
    private val updateTemplateInLocalUseCase: UpdateTemplateInLocalUseCase

) : ViewModel() {
    val currentSong = MutableStateFlow(
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

    val currentTemplate = mutableStateOf(
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

    val tempGlorifyingSongs = mutableStateListOf<Song>()
    val tempWorshipSongs = mutableStateListOf<Song>()
    val tempGiftSongs = mutableStateListOf<Song>()

    private val tempPerformerName = mutableStateOf(currentTemplate.value.performerName)
    val tempWeekday = mutableStateOf("Շաբաթվա օր")
    val planningDay = mutableStateOf("Ամսաթիվ")

    fun onSaveUpdates(current: Song, updatedSong: Song) {
        updateSongInFirebaseUseCase.execute(current, updatedSong)
    }


    fun updateTemplate(mode: TemplateSaveMode, new: SongTemplate, old: SongTemplate) {
        viewModelScope.launch {
            Log.e("MODE", "updateTemplate: mode :: $mode", )
            when (mode) {
                TemplateSaveMode.LOCAL -> {
                    updateTemplateInLocalUseCase.execute(new)
                }

                TemplateSaveMode.SERVER -> {
                    updateTemplateInFirebaseUseCase.execute(old, new)
                }
            }
        }
    }

    fun cleanFields() {
        tempGlorifyingSongs.clear()
        tempWorshipSongs.clear()
        tempGiftSongs.clear()

        tempPerformerName.value = ""
        tempWeekday.value = ""
        planningDay.value = ""
    }
}