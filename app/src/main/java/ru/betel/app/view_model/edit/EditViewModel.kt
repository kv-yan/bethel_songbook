package ru.betel.app.view_model.edit

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import ru.betel.domain.model.Song
import ru.betel.domain.model.SongTemplate
import ru.betel.domain.useCase.song.update.UpdateSongInFirebaseUseCase
import ru.betel.domain.useCase.template.update.UpdateTemplateInFirebaseUseCase

class EditViewModel(
    private val updateSongInFirebaseUseCase: UpdateSongInFirebaseUseCase,
    private val updateTemplateInFirebaseUseCase: UpdateTemplateInFirebaseUseCase

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

    fun onSaveUpdates(current: SongTemplate, updatedSong: SongTemplate) {
        updateTemplateInFirebaseUseCase.execute(current, updatedSong)
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